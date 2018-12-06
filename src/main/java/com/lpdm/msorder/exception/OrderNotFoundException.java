package com.lpdm.msorder.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(){
        super("No orders found");
    }
}
