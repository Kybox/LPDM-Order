package com.lpdm.msorder.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderedProductPK implements Serializable {

    @Column(name = "order_id")
    protected int orderId;

    @Column(name = "product_id")
    protected int productId;

    public OrderedProductPK() {}

    public OrderedProductPK(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(orderId, productId);
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        OrderedProductPK that = (OrderedProductPK) object;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);

    }
}
