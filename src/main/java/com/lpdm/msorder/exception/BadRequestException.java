package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class BadRequestException extends RuntimeException {

    public BadRequestException() {

        super("Bad request");
    }
}
