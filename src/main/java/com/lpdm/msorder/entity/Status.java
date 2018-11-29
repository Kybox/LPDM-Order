package com.lpdm.msorder.entity;

public enum Status {

    UNFINALIZED(1), PAID(2), PROCESSED(3), IN_DELIVERING(4), DELIVERED(5);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
