package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class OrderPersistenceException extends RuntimeException {

    public OrderPersistenceException(){
        super("Unable to persist this order");
    }
}
