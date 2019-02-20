package com.lpdm.msorder.model.product;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class Category {

    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name){
        this.id = id;
        this.name = name;
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
}
