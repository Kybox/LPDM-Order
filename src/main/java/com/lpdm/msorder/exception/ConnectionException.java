package com.lpdm.msorder.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
        super("The service connection failed");
    }
}
