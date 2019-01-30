package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.order.SearchDates;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface OrderService {

    /**
     * Order
     */
    Order saveOrder(Order order);
    void deleteOrder(Order order);
    Order findOrderById(int id) throws OrderNotFoundException;
    boolean checkIfOrderExist(int id);
    List<Order> findAllOrdersByCustomerId(int id);
    List<Order> findAllOrdersByPayment(Payment payment);

    List<Order> findAllOrdersByCustomer(String findBy, String keyword);

    List<Order> findAllOrdersPageable(PageRequest pageRequest);

    List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status);
    List<Order> findAllOrdersBetweenTwoDates(SearchDates searchDates);
    List<Order> findAllOrdersByStatusPageable(Status status, PageRequest pageRequest);
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateAsc(int id, PageRequest pageRequest);
    List<Order> findAllOrdersByCustomerIdOrderByOrderDateDesc(int id, PageRequest pageRequest);

    long countAllByOrderDateBetween(LocalDateTime date1, LocalDateTime date2);
    List<Order> findAllOrdersByDateBetween(LocalDateTime date1, LocalDateTime date2);

    /**
     * OrderedProduct
     */
    OrderedProduct saveOrderedProduct(OrderedProduct orderedProduct);
    List<OrderedProduct> getOrderedProductsByOrder(Order order);
    List<OrderedProduct> findAllOrderedProductsByProductId(int id);
    List<OrderedProduct> findAllOrderedProductsByOrder(Order order);
}
