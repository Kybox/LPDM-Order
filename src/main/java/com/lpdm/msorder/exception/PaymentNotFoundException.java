package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(){

        super("The payment was not found");
    }
}
