package com.lpdm.msorder.exception;

public class UserMalFormedException extends RuntimeException {

    public UserMalFormedException(String error){
        super(error);
    }
}
