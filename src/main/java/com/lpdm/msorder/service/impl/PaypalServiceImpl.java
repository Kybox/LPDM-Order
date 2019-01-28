package com.lpdm.msorder.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpdm.msorder.model.paypal.PaypalConfig;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.springframework.http.HttpHeaders.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final String SANDBOX_TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";

    @Override
    public String paypalPayment(String clientId, String clientSecret) {

        // Secrets
        APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");

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
            Payment createdPayment = payment.create(apiContext);

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

    @Override
    public PaypalToken generatePaypalToken(String cliendId, String secret) throws IOException {

        URL url = new URL(SANDBOX_TOKEN_URL);

        URLConnection urlConnection = url.openConnection();

        HttpURLConnection httpURLConnection = null;

        if(urlConnection instanceof HttpURLConnection)
            httpURLConnection = (HttpURLConnection) urlConnection;
        else {
            log.info("Please enter an HTTP URL");
            throw new IOException("HTTP URL is not correct");
        }

        String clearCredentials = cliendId + ":" + secret;
        String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(clearCredentials.getBytes());
        log.info("Authorization : " + basicAuth);

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpURLConnection.setRequestProperty(AUTHORIZATION, basicAuth);
        httpURLConnection.setRequestProperty(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeBytes("grant_type=client_credentials");
        dos.flush();
        dos.close();

        log.info(httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        String current;
        String urlContent = "";

        while ((current = bufferedReader.readLine()) != null){
            urlContent += current;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        PaypalToken paypalToken = objectMapper.readValue(urlContent, PaypalToken.class);

        log.info("Return : " + paypalToken);

        return paypalToken;
    }

    @Override
    public PaypalConfig getPaypalConfig(PaypalToken paypalToken) {

        PaypalConfig paypalConfig = new PaypalConfig();
        paypalConfig.setAuthToken(paypalToken.getAccess_token());
        paypalConfig.setPostUrl("https://sandbox.paypal.com/cgi-bin/websrc");
        paypalConfig.setBusiness("lpdm.paypal@gmail.com");
        paypalConfig.setReturnUrl("http://localhost:28083/return");

        return paypalConfig;
    }

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

        /*
        Payment payment = new Payment();
        payment.setId(paypalReturn.getPaymentId());

        try{
            Payment createdPayment = payment.create(apiContext);

            log.info("ObjectDetails : " + createdPayment.toString());

            // Identifier of the payment resource created
            payment.setId(createdPayment.getId());

            PaymentExecution paymentExecution = new PaymentExecution();

            // Set your PayerID. The ID of the Payer, passed in the return_url by PayPal.
            paymentExecution.setPayerId(paypalReturn.getPayerID());

            // This call will fail as user has to access Payment on UI. Programmatically
            // there is no way you can get Payer's consent.
            Payment createdAuthPayment = payment.execute(apiContext, paymentExecution);

            // Transactional details including the amount and item details.
            Authorization authorization = createdAuthPayment.getTransactions()
                    .get(0).getRelatedResources().get(0).getAuthorization();

            log.info("Here is your Authorization ID : " + authorization.getId());
            log.info("Cart = " + createdAuthPayment.getCart());

            log.info(createdPayment.toString());


        } catch (PayPalRESTException e) {
            log.error(String.valueOf(e.getDetails()));
        }

        */

    }

}
