package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.service.PaymentService;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.RedirectUrls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${paypal.clientid}")
    private String paypalClientId;

    @Value(("${paypal.secret}"))
    private String paypalSecret;

    private final PaymentService paymentService;

    private final PaypalService paypalService;

    @Autowired
    public PaymentController(PaypalService paypalService, PaymentService paymentService) {
        this.paypalService = paypalService;
        this.paymentService = paymentService;
    }

    @PostMapping(value = "orders/{id}/pay",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaypalPayUrl payOrder(@PathVariable int id,
                                 @Valid @RequestBody RedirectUrls redirectUrls) throws IOException {

        log.info("Order id = " + id);
        log.info("URL return = " + redirectUrls.getReturnUrl());
        log.info("URL cancel = " + redirectUrls.getCancelUrl());

        return paymentService.paypalPaymentProcess(id, redirectUrls);

    }

    /*
    @GetMapping(value = "/paypal", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView setPayment(HttpServletResponse response){

        PaypalToken paypalToken = null;
        String redirectUrl = null;

        //paypalService.paypalPayment(paypalClientId, paypalSecret);

        try {
            paypalToken = paypalService.generatePaypalToken(paypalClientId, paypalSecret);
            redirectUrl = paypalService.paypalPayment(paypalClientId, paypalSecret);

            log.info("redirect URL = " + redirectUrl);

            response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + paypalToken.getAccess_token());
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return new ModelAndView("redirect:" + redirectUrl);



    }

    */

    @GetMapping(value = "/paypal/cancel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cancel(){
        return "CANCEL";
    }

    @GetMapping(value = "/paypal/return")
    public String returnPayment(@ModelAttribute PaypalReturn paypalReturn){


        log.info("Return Object = " + paypalReturn);

        paypalService.getTransactionDetails(paypalReturn, paypalClientId, paypalSecret);


        return "PAYMENT SUCESSUL";
    }
}
