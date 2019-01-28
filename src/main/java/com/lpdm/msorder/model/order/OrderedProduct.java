package com.lpdm.msorder.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.product.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ordered_product", schema = "public")
public class OrderedProduct {

    @Id
    @JsonIgnore
    @GeneratedValue
    private int id;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @JsonIgnore
    @Column(name = "product_id")
    private int productId;

    @NotNull
    @Transient
    private Product product;

    @Column
    @NotNull
    private int quantity;

    @Column
    @NotNull
    private double price;

    @Column
    @NotNull
    private double tax;

    public OrderedProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", tax=" + tax +
                '}';
    }
}