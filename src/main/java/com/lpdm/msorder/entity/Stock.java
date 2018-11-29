package com.lpdm.msorder.entity;

import java.time.LocalDate;

public class Stock {

    private int id;
    private Category category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
