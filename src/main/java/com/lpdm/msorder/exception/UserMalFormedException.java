package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class UserMalFormedException extends RuntimeException {

    public UserMalFormedException(String error){
        super(error);
    }
}
