package com.lpdm.msorder.entity;

public enum Payment {

    BANKCARD(1), PAYPAL(2);

    private int id;

    Payment(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
