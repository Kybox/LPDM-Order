package com.lpdm.msorder.model.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "delivery", schema = "public")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(length = 30)
    private String method;

    public Delivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", method='" + method + '\'' +
                '}';
    }
}
