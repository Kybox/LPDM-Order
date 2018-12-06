package com.lpdm.msorder.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment", schema = "public")
public class Payment {

    @Id
    @Column(updatable = false, nullable = false)
    @SequenceGenerator(name = "pay_gen", sequenceName = "pay_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_gen")
    private int id;

    @NotNull
    @Column(unique = true, length = 20)
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
