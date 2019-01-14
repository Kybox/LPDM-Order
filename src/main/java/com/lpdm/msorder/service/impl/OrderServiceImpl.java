package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PaymentRepository paymentRepository,
                            OrderedProductRepository orderedProductRepository) {

        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Optional<Order> findOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllOrdersByCustomerId(int id) {
        return orderRepository.findAllByCustomerId(id);
    }

    @Override
    public List<Order> findAllOrdersByPayment(Payment payment) {
        return orderRepository.findAllByPayment(payment);
    }

    @Override
    public Page<Order> findAllOrdersPageable(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status) {
        return orderRepository.findAllByCustomerIdAndStatus(id, status);
    }

    @Override
    public List<Order> findAllOrdersByStatusPageable(Status status, PageRequest pageRequest) {
        return orderRepository.findAllByStatus(status, pageRequest);
    }

    @Override
    public List<Order> findAllOrdersByCustomerIdOrderByOrderDateAsc(int id, PageRequest pageRequest) {
        return orderRepository.findAllByCustomerIdOrderByOrderDateAsc(id, pageRequest);
    }

    @Override
    public List<Order> findAllOrdersByCustomerIdOrderByOrderDateDesc(int id, PageRequest pageRequest) {
        return orderRepository.findAllByCustomerIdOrderByOrderDateDesc(id, pageRequest);
    }

    @Override
    public OrderedProduct saveOrderedProduct(OrderedProduct orderedProduct) {
        return orderedProductRepository.save(orderedProduct);
    }

    @Override
    public List<OrderedProduct> getOrderedProductsByOrder(Order order) {
        return orderedProductRepository.findAllByOrder(order);
    }

    @Override
    public List<OrderedProduct> findAllOrderedProductsByProductId(int id) {
        return orderedProductRepository.findAllByProductId(id);
    }

    @Override
    public List<OrderedProduct> findAllOrderedProductsByOrder(Order order) {
        return orderedProductRepository.findAllByOrder(order);
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Payment payment) {
        paymentRepository.delete(payment);
    }
}
