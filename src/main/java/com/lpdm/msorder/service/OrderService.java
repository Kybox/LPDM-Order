package com.lpdm.msorder.service;

import com.lpdm.msorder.model.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * Order
     */
    Order saveOrder(Order order);
    Optional<Order> findOrderById(int id);
    List<Order> findAllOrdersByCustomerId(int id);
    List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status);
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateAsc(int id, PageRequest pageRequest);
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateDesc(int id, PageRequest pageRequest);

    /**
     * OrderedProduct
     */
    OrderedProduct saveOrderedProduct(OrderedProduct orderedProduct);
    List<OrderedProduct> getOrderedProductsByOrder(Order order);
    List<OrderedProduct> findAllOrderedProductsByOrder(Order order);

    /**
     * Product
     */
    Optional<Product> getProductById(int id);

    /**
     * Payment
     */
    List<Payment> findAllPayments();
}
