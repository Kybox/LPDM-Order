package com.lpdm.msorder.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(){
        super("No order was found");
    }
}
