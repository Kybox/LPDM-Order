package com.lpdm.msorder.exception;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(){

        super("Delivery method not found");
    }
}
