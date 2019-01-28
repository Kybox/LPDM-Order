package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${paypal.clientid}")
    private String paypalClientId;

    @Value(("${paypal.secret}"))
    private String paypalSecret;

    private final PaypalService paypalService;

    @Autowired
    public PaymentController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

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
