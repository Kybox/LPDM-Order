package com.lpdm.msorder.model.store;

import com.lpdm.msorder.model.location.Address;

public class Store {

    private int id;
    private String name;
    private Address location;

    public Store() {
    }

    public Store(int id) {
        this.id = id;
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

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }
}