package com.lpdm.msorder.model.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Entity
@Table(name = "payment", schema = "public")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true, length = 20)
    private String label;

    public Payment() {
    }

    public Payment(@NotNull String label) {
        this.label = label;
    }

    public Payment(int id){
        this.id = id;
    }

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
