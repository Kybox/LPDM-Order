package com.lpdm.msorder.model.entity;

public class EntityErrorDetails {

    private String message;
    private String details;

    public EntityErrorDetails(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }
}
