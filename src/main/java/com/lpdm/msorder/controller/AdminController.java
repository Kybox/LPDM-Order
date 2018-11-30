package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.PaymentDao;
import com.lpdm.msorder.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RefreshScope
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final PaymentDao paymentDao;

    @Autowired
    public AdminController(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @PostMapping(value = "/addNewPayment", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment addNewPayment(@Valid @RequestBody Payment payment){
        return paymentDao.save(payment);
    }
}
