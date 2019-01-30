package com.lpdm.msorder.exception.handler;

import com.lpdm.msorder.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AddressNotFoundException.class)
    public String addressNotFoundHandler(AddressNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String badRequestHandler(BadRequestException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ConnectionException.class)
    public String connectionHandler(ConnectionException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(DeleteEntityException.class)
    public String deleteEntityHandler(DeleteEntityException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DeliveryNotFoundException.class)
    public String deliveryMethodNotFoundHandler(DeliveryNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderedProductsNotFoundException.class)
    public String orderedProductsNotFoundHandler(OrderedProductsNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public String orderNotFoundHandler(OrderNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(OrderPersistenceException.class)
    public String orderPersistenceHandler(OrderPersistenceException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(PaymentPersistenceException.class)
    public String paymentPersistenceHandler(PaymentPersistenceException e){
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvoiceNotFoundException.class)
    public String invoiceNotFoundHandler(InvoiceNotFoundException e){
        return e.getMessage();
    }
}
