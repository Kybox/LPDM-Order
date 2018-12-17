package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.ConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConnectionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ConnectionException.class)
    public String connectionHandler(ConnectionException e){
        return e.getMessage();
    }
}
