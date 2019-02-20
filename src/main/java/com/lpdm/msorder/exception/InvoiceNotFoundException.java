package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(){

        super("No invoice found for this order");
    }
}
