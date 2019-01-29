package com.lpdm.msorder.exception;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(){

        super("The user's address was not found");
    }
}
