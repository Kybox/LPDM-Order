package com.lpdm.msorder.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpdm.msorder.model.location.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class User {

    private int id;
    private String firstName;
    private String name;
    private Address address;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int addressId;
    private String email;
    private String tel;
    private LocalDate birthday;
    private LocalDateTime registrationDate;
    private boolean active;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", addressId=" + addressId +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
