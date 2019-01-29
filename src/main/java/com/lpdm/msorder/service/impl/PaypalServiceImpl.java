package com.lpdm.msorder.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpdm.msorder.model.location.Address;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lpdm.msorder.utils.ValueType.*;
import static com.paypal.base.Constants.*;
import static org.springframework.http.HttpHeaders.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    @Autowired
    public PaypalServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public List<Item> generateOrderedProducts(List<OrderedProduct> orderedProductList) {

        if(orderedProductList == null) log.info("OrderedProducts List = null");
        else log.info("There are " + orderedProductList.size() + " ordered products");

        List<Item> itemList = new ArrayList<>();

        for(OrderedProduct orderedProduct : orderedProductList){

            Item item = new Item();
            item.setCurrency(CURRENCY_EURO);
            item.setName(orderedProduct.getProduct().getName());
            item.setDescription(orderedProduct.getProduct().getLabel());
            item.setCategory("PHYSICAL");
            item.setQuantity(String.valueOf(orderedProduct.getQuantity()));
            item.setPrice(String.valueOf(orderedProduct.getPrice()));
            double tax = (Double.parseDouble(item.getPrice()) * orderedProduct.getTax()) / 100;
            item.setTax(String.valueOf(tax));
            itemList.add(item);
        }

        return itemList;
    }

    @Override
    public ShippingAddress generateShippingAddress(User user) {

        Address address = user.getAddress();
        ShippingAddress shippingAddress = new ShippingAddress();

        String recipientName = user.getFirstName() + SPACE + user.getName();
        String addressline1 = address.getStreetNumber() + SPACE + address.getStreetName() + SPACE;

        shippingAddress.setRecipientName(recipientName);
        shippingAddress.setLine1(addressline1);
        shippingAddress.setLine2(address.getComplement());
        shippingAddress.setPostalCode(address.getCity().getZipCode());
        shippingAddress.setCity(address.getCity().getName());
        shippingAddress.setCountryCode("FR");

        return shippingAddress;
    }

    @Override
    public ItemList generateItemList(List<Item> items,
                                     String phoneNumber,
                                     ShippingAddress shippingAddress) {

        ItemList itemList = new ItemList();
        itemList.setItems(items);
        itemList.setShippingAddress(shippingAddress);
        itemList.setShippingPhoneNumber(phoneNumber);
        itemList.setShippingMethod("Livraison à domicile");

        return itemList;
    }

    @Override
    public PaypalToken generatePaypalToken() throws IOException {

        URL url = new URL(PAYPAL_SANDBOX_TOKEN_URL);

        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpConnection = null;

        if(urlConnection instanceof HttpURLConnection)
            httpConnection = (HttpURLConnection) urlConnection;
        else throw new IOException("HTTP URL is not correct");

        String credentials = env.getProperty("paypal.clientid") + ":" + env.getProperty("paypal.secret");
        String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes());

        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod(HTTP_CONFIG_DEFAULT_HTTP_METHOD);
        httpConnection.setRequestProperty(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpConnection.setRequestProperty(AUTHORIZATION, basicAuth);
        httpConnection.setRequestProperty(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        DataOutputStream dos = new DataOutputStream(httpConnection.getOutputStream());
        dos.writeBytes("grant_type=client_credentials");
        dos.flush();
        dos.close();

        log.info(httpConnection.getResponseCode() + " " + httpConnection.getResponseMessage());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        String current;
        StringBuilder urlContent = new StringBuilder();

        while ((current = bufferedReader.readLine()) != null){
            urlContent.append(current);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(urlContent.toString(), PaypalToken.class);
    }

    @Override
    public String paymentProcess(int orderId, ItemList itemList, RedirectUrls redirectUrls) {

        Details details = getPaypalDetails(itemList);
        Amount amount = getPaypalAmount(details);

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Commande n°" + orderId);
        transaction.setItemList(itemList);

        // Add transaction to a list
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        // Add payment details
        Payment payment = new Payment();

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        //payment.setIntent("authorize");
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactionList);
        payment.setRedirectUrls(redirectUrls);

        try{

            Payment createdPayment = payment.create(getPaypalApiContext(SANDBOX));
            payment.setId(createdPayment.getId());

            log.info("ObjectDetails : " + createdPayment.toString());

            Iterator links = createdPayment.getLinks().iterator();

            while (links.hasNext()){
                Links link = (Links) links.next();
                if(link.getRel().equalsIgnoreCase("approval_url")){
                    // Redirect the customer to link.getHref()
                    return link.getHref();
                }
            }
        }
        catch (PayPalRESTException e) {
            log.error(String.valueOf(e.getDetails()));
        }

        return null;
    }

    private APIContext getPaypalApiContext(String mode){

        String client = env.getProperty("paypalClientId");
        String secret = env.getProperty("paypalSecret");

        return new APIContext(client, secret, mode);
    }

    private Details getPaypalDetails(ItemList itemList){

        Details details = new Details();
        double subTotal = 0;
        double tax = 0;
        for(Item item : itemList.getItems()){

            double itemPrice = Double.parseDouble(item.getPrice());
            double itemTax = Double.parseDouble(item.getTax());

            subTotal += itemPrice;
            tax += itemTax;
        }

        details.setSubtotal(String.valueOf(subTotal));
        details.setTax(String.valueOf(tax));
        details.setShipping("4.90");

        return details;
    }

    private Amount getPaypalAmount(Details details){

        Amount amount = new Amount();

        double subTotal = Double.parseDouble(details.getSubtotal());
        double shipping = Double.parseDouble(details.getShipping());
        double tax = Double.parseDouble(details.getTax());

        amount.setTotal(String.valueOf(subTotal + shipping + tax));
        amount.setDetails(details);
        amount.setCurrency("EUR");

        return amount;
    }


    /*
    @Override
    public String paypalPayment() {

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://order.lpdm.kybox.fr/paypal/cancel");
        redirectUrls.setReturnUrl("https://order.lpdm.kybox.fr/paypal/return");


        Item item1 = new Item();
        item1.setCurrency("EUR");
        item1.setDescription("Certifié label BIO");
        item1.setName("Tomates marmandaise");
        item1.setCategory("PHYSICAL");
        item1.setPrice("4.80");
        item1.setQuantity("2");

        Item item2 = new Item();
        item2.setCurrency("EUR");
        item2.setDescription("Botte de 5 carottes");
        item2.setName("Carottes");
        item2.setCategory("PHYSICAL");
        item2.setPrice("3.60");
        item2.setQuantity("1");

        List<Item> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);


        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setRecipientName("Gilbert Montagné");
        shippingAddress.setLine1("10 rue des Tropiques");
        shippingAddress.setPostalCode("33000");
        shippingAddress.setCity("Bordeaux");
        shippingAddress.setCountryCode("FR");


        ItemList itemList = new ItemList();
        itemList.setItems(list);
        itemList.setShippingAddress(shippingAddress);
        itemList.setShippingMethod("Méthode d'envoi du colis");
        itemList.setShippingPhoneNumber("+33 0699990000");



        // Set payment details
        Details details = new Details();
        details.setSubtotal("13.20");
        details.setTax("0.73");
        details.setShipping("4.90");

        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal("18.83");
        amount.setDetails(details);

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Commande n°1365");
        transaction.setItemList(itemList);

        // Add transaction to a list
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        // Add payment details
        Payment payment = new Payment();
        //payment.setIntent("authorize");
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactionList);
        payment.setRedirectUrls(redirectUrls);

        try{
            Payment createdPayment = payment.create(getPaypalApiContext(SANDBOX));

            log.info("ObjectDetails : " + createdPayment.toString());

            // Identifier of the payment resource created
            payment.setId(createdPayment.getId());

            PaymentExecution paymentExecution = new PaymentExecution();

            // Set your PayerID. The ID of the Payer, passed in the return_url by PayPal.
            //paymentExecution.setPayerId("UNRL6DY5RWTZJ");

            // This call will fail as user has to access Payment on UI. Programmatically
            // there is no way you can get Payer's consent.
            //Payment createdAuthPayment = payment.execute(apiContext, paymentExecution);

            // Transactional details including the amount and item details.
            //Authorization authorization = createdAuthPayment.getTransactions()
            //        .get(0).getRelatedResources().get(0).getAuthorization();

            //log.info("Here is your Authorization ID : " + authorization.getId());


            Iterator links = createdPayment.getLinks().iterator();

            while (links.hasNext()){
                Links link = (Links) links.next();
                if(link.getRel().equalsIgnoreCase("approval_url")){
                    // Redirect the customer to link.getHref()
                    return link.getHref();
                }
            }

            log.info(createdPayment.toString());


        } catch (PayPalRESTException e) {
            log.error(String.valueOf(e.getDetails()));
        }


        return null;
    }
    */

    @Override
    public void getTransactionDetails(PaypalReturn paypalReturn, String cliendId, String secret) {

        APIContext apiContext = new APIContext(cliendId, secret, "sandbox");

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(paypalReturn.getPayerID());

        Payment payment = new Payment();
        payment.setId(paypalReturn.getPaymentId());

        try {
            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            log.info("Payment execution : ");
            log.info(executedPayment.toJSON());

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }



}
