package com.lpdm.msorder.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpdm.msorder.exception.AddressNotFoundException;
import com.lpdm.msorder.exception.OrderedProductsNotFoundException;
import com.lpdm.msorder.model.location.Address;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.OrderService;
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

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    @Autowired
    private OrderService orderService;

    @Autowired
    public PaypalServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public List<Item> generateOrderedProducts(List<OrderedProduct> orderedProductList)
            throws OrderedProductsNotFoundException {

        if(orderedProductList == null) throw new OrderedProductsNotFoundException();
        if(orderedProductList.size() == 0) throw new OrderedProductsNotFoundException();

        List<Item> itemList = new ArrayList<>();

        for(OrderedProduct orderedProduct : orderedProductList){

            log.info("Product : " + orderedProduct.getProduct());

            Item item = new Item();
            item.setCurrency(CURRENCY_EURO);
            item.setName(orderedProduct.getProduct().getName());
            item.setDescription(orderedProduct.getProduct().getLabel());
            item.setCategory("PHYSICAL");
            item.setQuantity(String.valueOf(orderedProduct.getQuantity()));
            item.setPrice(String.valueOf(orderedProduct.getPrice()));

            double tax = (orderedProduct.getPrice() * orderedProduct.getQuantity()) * (orderedProduct.getTax() / 100);
            log.info("tax before round = " + tax);

            tax = Math.round(tax * 100D) / 100D;
            log.info("tax after round = " + tax);

            item.setTax(String.valueOf(tax));
            itemList.add(item);

            log.info("Item");
            log.info("   |_ price = " + item.getPrice());
            log.info("   |_ tax = " + item.getTax());
        }

        return itemList;
    }

    @Override
    public ShippingAddress generateShippingAddress(User user)
            throws AddressNotFoundException {

        Address address = user.getAddress();

        if(address == null || address.getId() == 0)
            throw new AddressNotFoundException();

        ShippingAddress shippingAddress = new ShippingAddress();

        String recipientName = user.getFirstName() + SPACE + user.getName();

        String addressline1 = address.getStreetNumber() + SPACE + address.getStreetName() + SPACE;

        shippingAddress.setRecipientName(recipientName);
        shippingAddress.setLine1(addressline1);

        if(address.getComplement() != null) shippingAddress.setLine2(address.getComplement());
        else shippingAddress.setLine2("");

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
    public String paymentProcess(int orderId, double deliveryAmount, ItemList itemList, RedirectUrls redirectUrls, String id, String secret) {

        Details details = getPaypalDetails(itemList, deliveryAmount);
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

        try {

            APIContext apiContext = new APIContext(id, secret, SANDBOX);
            Payment createdPayment = payment.create(apiContext);
            payment.setId(createdPayment.getId());

            log.info("ObjectDetails : " + createdPayment.toString());

            Iterator links = createdPayment.getLinks().iterator();

            while (links.hasNext()) {
                Links link = (Links) links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    // Redirect the customer to link.getHref()
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(String.valueOf(e.getDetails()));
        }

        return null;
    }

    @Override
    public String getTransactionDetails(PaypalReturn paypalReturn,
                                        String cliendId,
                                        String secret) throws PayPalRESTException {

        APIContext apiContext = new APIContext(cliendId, secret, SANDBOX);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(paypalReturn.getPayerID());

        Payment payment = new Payment();
        payment.setId(paypalReturn.getPaymentId());

        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        log.info("Payment execution : ");
        log.info(executedPayment.toJSON());

        return executedPayment.toJSON();
    }

    private Details getPaypalDetails(ItemList itemList, double deliveryAmount){

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
        details.setShipping(String.valueOf(deliveryAmount));

        return details;
    }

    private Amount getPaypalAmount(Details details){

        Amount amount = new Amount();

        double subTotal = Double.parseDouble(details.getSubtotal());
        double shipping = Double.parseDouble(details.getShipping());
        double tax = Double.parseDouble(details.getTax());
        double total = subTotal + shipping + tax;

        log.info("total = " + total);
        total = Math.round(total * 100D) / 100D;
        log.info("total after = " + total);

        amount.setTotal(String.valueOf(total));
        amount.setDetails(details);
        amount.setCurrency(CURRENCY_EURO);

        log.info("Amount");
        log.info("  |_currency = " + amount.getCurrency());
        log.info("  |_total = " + amount.getTotal());
        log.info("  |_subtotal = " + amount.getDetails().getSubtotal());
        log.info("  |_shipping = " + amount.getDetails().getShipping());
        log.info("  |_tax = " + amount.getDetails().getTax());

        return amount;
    }
}
