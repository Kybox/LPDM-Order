package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final ProxyService proxyService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderedProductRepository orderedProductRepository,
                            ProxyService proxyService, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.proxyService = proxyService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
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
    public List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status) {
        return orderRepository.findAllByCustomerIdAndStatus(id, status);
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
    public List<OrderedProduct> findAllOrderedProductsByOrder(Order order) {
        return orderedProductRepository.findAllByOrder(order);
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return proxyService.findProductById(id);
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
}
