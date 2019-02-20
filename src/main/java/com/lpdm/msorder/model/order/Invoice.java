package com.lpdm.msorder.model.order;

import javax.persistence.*;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Entity
@Table(name = "invoice", schema = "public")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "reference", length = 50)
    private String reference;

    public Invoice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", reference='" + reference + '\'' +
                '}';
    }
}
