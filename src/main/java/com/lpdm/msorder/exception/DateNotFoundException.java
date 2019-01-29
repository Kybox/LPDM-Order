package com.lpdm.msorder.exception;

public class DateNotFoundException extends RuntimeException {

    public DateNotFoundException(){
        super("Date not found");
    }
}
