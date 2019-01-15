package com.lpdm.msorder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Product {

    @NotNull
    private int id;
    private String name;
    private Category category;
    private String label;

    @NotNull
    private double price;
    private User producer;
    private String picture;
    private double tva;

    @JsonIgnore
    private int productorID;

    private List<Stock> listStock;

    public Product() {
    }

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, double price){
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
        this.producer = producer;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public int getProductorID() {
        return productorID;
    }

    public void setProductorID(int productorID) {
        this.productorID = productorID;
    }

    public List<Stock> getListStock() {
        return listStock;
    }

    public void setListStock(List<Stock> listStock) {
        this.listStock = listStock;
    }
}