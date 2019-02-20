package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
        super("The service connection failed");
    }
}
