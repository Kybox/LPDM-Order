package com.lpdm.msorder.model.order;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public enum Status {

    CART(1), VALIDATED(2) , PAID(3), PROCESSED(4), IN_DELIVERING(5), DELIVERED(6), CANCELLED(7);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
