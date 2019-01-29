package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.AddressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AddressNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AddressNotFoundException.class)
    String addressNotFoundHandler(AddressNotFoundException e){
        return e.getMessage();
    }
}
