package com.lpdm.msorder.model.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(length = 10)
    private String code;

    public Coupon() {
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

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", active=" + active +
                ", amount=" + amount +
                ", code='" + code + '\'' +
                '}';
    }
}
