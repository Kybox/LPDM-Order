package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class DateNotFoundException extends RuntimeException {

    public DateNotFoundException(){
        super("Date not found");
    }
}
