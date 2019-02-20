package com.lpdm.msorder.model.product;

import java.time.LocalDate;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class Stock {

    private int id;
    private int quantity;
    private LocalDate expireDate;
    private String packaging;
    private int unitByPackage;

    public Stock() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public int getUnitByPackage() {
        return unitByPackage;
    }

    public void setUnitByPackage(int unitByPackage) {
        this.unitByPackage = unitByPackage;
    }
}
