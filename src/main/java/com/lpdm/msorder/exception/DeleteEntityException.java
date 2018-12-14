package com.lpdm.msorder.exception;

public class DeleteEntityException extends RuntimeException {

    public DeleteEntityException() {
        super("Entity deleting failed");
    }
}
