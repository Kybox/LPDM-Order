package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class PaymentPersistenceException extends RuntimeException {

    public PaymentPersistenceException(){
        super("Unable to persist this payment");
    }
}
