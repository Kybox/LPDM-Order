package com.lpdm.msorder.service;

import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.model.OrderedProduct;
import com.lpdm.msorder.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * Order
     */
    Optional<Order> getById(int id);

    /**
     * OrderedProduct
     */
    List<OrderedProduct> getOrderedProductsByOrder(Order order);

    /**
     * Product
     */
    Optional<Product> getProductById(int id);
}
