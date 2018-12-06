package com.lpdm.msorder.exception;

public class OrderPersistenceException extends RuntimeException {

    public OrderPersistenceException(){
        super("Unable to persist this order");
    }
}
