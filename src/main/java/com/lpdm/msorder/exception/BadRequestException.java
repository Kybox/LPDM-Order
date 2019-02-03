package com.lpdm.msorder.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {

        super("Bad request");
    }
}
