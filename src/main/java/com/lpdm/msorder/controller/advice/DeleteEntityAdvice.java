package com.lpdm.msorder.controller.advice;

import com.lpdm.msorder.exception.DeleteEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DeleteEntityAdvice {

    @ResponseBody
    @ExceptionHandler(DeleteEntityException.class)
    public ResponseEntity<Object> deleteEntityHandler(DeleteEntityException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
