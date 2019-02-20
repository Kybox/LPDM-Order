package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(){

        super("The user's address was not found");
    }
}
