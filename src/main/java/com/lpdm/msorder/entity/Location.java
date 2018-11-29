package com.lpdm.msorder.entity;

public class Location {

    private int id;
    private String streetName;
    private String streetNumber;
    private String complement;
    private int cityId;

    public Location() {
    }

    public Location(int id, String streetName, String streetNumber, String complement, int cityId) {
        this.id = id;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.complement = complement;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}