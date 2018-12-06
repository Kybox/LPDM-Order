package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.OrderPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderPersistenceExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(OrderPersistenceException.class)
    String orderPersistenceException(OrderPersistenceException e){
        return e.getMessage();
    }
}
