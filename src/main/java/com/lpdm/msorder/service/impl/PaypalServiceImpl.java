package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PaypalServiceImpl implements PaypalService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String paypalPayment() {

        // Secrets
        String clientId = "AXiaPcOEAfAhMqnqSJHPKRg8JIh0_ndSO1umLaQGAhVJnN5tK4AKuGLgpXfqpcYnC6afk6U9g_5ltIUM";
        String clientSecret = "EPAoCMbjSmf1w7lxvvi7i2C4vfi_vGHWEsSl4Q_CQw6NAjA4o_KteTW8FBMjj_bLFcfCpbijvO38t1Dx";
        APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:28083/paypal/cancel");
        redirectUrls.setReturnUrl("http://localhost:28083/paypal/return");

        // Set payment details
        Details details = new Details();
        details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");

        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal("7");
        amount.setDetails(details);

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment transaction description.");

        // Add transaction to a list
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        // Add payment details
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactionList);
        payment.setRedirectUrls(redirectUrls);

        try{
            Payment createdPayment = payment.create(apiContext);
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
}
