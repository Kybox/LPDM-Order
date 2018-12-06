package com.lpdm.msorder.exception;

public class PaymentPersistenceException extends RuntimeException {

    public PaymentPersistenceException(){
        super("Unable to persist this payment");
    }
}
