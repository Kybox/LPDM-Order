package com.lpdm.msorder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order", schema = "public")
public class Order {

    @Id
    @Column(updatable = false, nullable = false)
    @SequenceGenerator(name = "order_gen", sequenceName = "order_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    private int id;

    @Column
    private double total;

    @Column(name = "order_date")
    private LocalDateTime dateTime;

    @Column(name = "status_id")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @JsonIgnore
    @Column(name = "store_id")
    private int storeId;

    @Transient
    private Store store;

    @NotNull
    @JsonIgnore
    @Column(name = "customer_id")
    private int customerId;

    @Transient
    private User customer;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderedProduct> orderedProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}