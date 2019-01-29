package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.DeliveryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeliveryNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DeliveryNotFoundException.class)
    String deliveryMethodNotFoundHandler(DeliveryNotFoundException e){
        return e.getMessage();
    }
}
