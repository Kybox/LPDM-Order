package com.lpdm.msorder.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(){

        super("The payment was not found");
    }
}
