package com.lpdm.msorder.exception;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(){

        super("No invoice found for this order");
    }
}
