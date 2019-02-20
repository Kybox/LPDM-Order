package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(){

        super("Delivery method not found");
    }
}
