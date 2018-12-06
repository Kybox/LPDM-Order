package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.PaymentPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PaymentPersistenceExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(PaymentPersistenceException.class)
    String paymentPersistenceException(PaymentPersistenceException e){
        return e.getMessage();
    }
}
