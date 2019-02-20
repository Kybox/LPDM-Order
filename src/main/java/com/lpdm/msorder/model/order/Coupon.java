package com.lpdm.msorder.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Entity
@Table(name = "coupon", schema = "public")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean active;

    @NotNull
    @Column
    private double amount;

    @NotNull
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;

    @Column
    private String description;

    public Coupon() {
    }

    public Coupon(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", active=" + active +
                ", amount=" + amount +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
