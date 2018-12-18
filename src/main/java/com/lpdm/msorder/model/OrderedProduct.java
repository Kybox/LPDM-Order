package com.lpdm.msorder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ordered_product", schema = "public")
public class OrderedProduct {

    @EmbeddedId
    @JsonIgnore
    private OrderedProductPK orderedProductPK = new OrderedProductPK();

    @ManyToOne
    @MapsId("orderId")
    @JsonBackReference
    private Order order;

    @Transient
    private Product product;

    @Column
    private int quantity;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }
}