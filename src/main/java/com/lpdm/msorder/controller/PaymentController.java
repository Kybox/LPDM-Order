package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.service.PaymentService;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.lpdm.msorder.utils.ValueType.ORDERS_PATH;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RefreshScope
@RestController
@RequestMapping(ORDERS_PATH)
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${paypal.clientid}")
    private String clientId;

    @Value(("${paypal.secret}"))
    private String secret;

    private final PaymentService paymentService;
    private final PaypalService paypalService;

    @Autowired
    public PaymentController(PaypalService paypalService, PaymentService paymentService) {
        this.paypalService = paypalService;
        this.paymentService = paymentService;
    }

    /**
     * Pay for the order with Paypal
     * @param id The {@link Order} id
     * @param redirectUrls The redirection urls required by Paypal
     * @return {@link PaypalPayUrl} object
     * @throws IOException Exception thrown by the paypal service if it encounters an error
     */
    @PostMapping(value = "/{id}/pay",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaypalPayUrl payOrder(@PathVariable int id,
                                 @Valid @RequestBody RedirectUrls redirectUrls) throws Exception {

        log.info("Paypal payment process for order id " + id);
        log.info("|_ Redirection URLs transmitted by the request");
        log.info("   + URL return = " + redirectUrls.getReturnUrl());
        log.info("   + URL cancel = " + redirectUrls.getCancelUrl());

        return paymentService.paypalPaymentProcess(id, redirectUrls, clientId, secret);
    }

    @GetMapping(value = "/transaction/details")
    public String returnPayment(@ModelAttribute PaypalReturn paypalReturn) throws PayPalRESTException {

        return paypalService.getTransactionDetails(paypalReturn, clientId, secret);
    }

    /**
     * Get all {@link Payment} recorded in the database
     * @return The {@link List <Payment>}
     */
    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Payment> getPaymentList(){

        return paymentService.findAllPayments();
    }
}
