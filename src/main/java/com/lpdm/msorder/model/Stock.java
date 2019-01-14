package com.lpdm.msorder.model;

import java.time.LocalDateTime;

public class Stock {

    private int id;
    private int quantity;
    private LocalDateTime expireDate;
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

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
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
