package com.lpdm.msorder.controller.json;

import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PaymentController {

    private final PaypalService paypalService;

    @Autowired
    public PaymentController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @GetMapping(value = "/paypal", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView setPayment(){

        String url =  paypalService.paypalPayment();
        return new ModelAndView("redirect:" + url);

    }

    @GetMapping(value = "/paypal/cancel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cancel(){
        return "CANCEL";
    }

    @GetMapping(value = "/paypal/return", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String returnPayment(){
        return "RETURN";
    }
}
