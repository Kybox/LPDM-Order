package com.lpdm.msorder.model.location;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class Address {

    private int id;
    private String streetName;
    private String streetNumber;
    private String complement;
    private City city;

    public Address() {
    }

    public Address(int id){
        this.id = id;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", city=" + city +
                '}';
    }
}