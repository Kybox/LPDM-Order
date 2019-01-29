package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.DeliveryNotFoundException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.OrderStats;
import com.lpdm.msorder.model.user.SearchDates;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.repository.DeliveryRepository;
import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.service.DeliveryService;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import com.lpdm.msorder.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ProxyService proxyService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            PaymentRepository paymentRepository,
                            OrderedProductRepository orderedProductRepository,
                            ProxyService proxyService) {

        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.proxyService = proxyService;
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
    public List<Order> findAllOrdersByCustomerEmail(String email) {

        Optional<User> optUser = proxyService.findUserByEmail(email);
        log.info("USER = " + optUser.get());
        if(!optUser.isPresent()) throw new OrderNotFoundException();
        return orderRepository.findAllByCustomerId(optUser.get().getId());
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
    public List<Order> findAllOrdersByCustomerLastName(String lastName) {

        Optional<User> optUser = proxyService.findUserByLastName(lastName);
        if(!optUser.isPresent()) throw new OrderNotFoundException();
        return orderRepository.findAllByCustomerId(optUser.get().getId());
    }

    @Override
    public List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status) {
        return orderRepository.findAllByCustomerIdAndStatus(id, status);
    }

    @Override
    public List<Order> findAllOrdersBetweenTwoDates(SearchDates searchDates) {
        LocalDateTime dateTime1 = searchDates.getDate1().atStartOfDay();
        LocalDateTime dateTime2 = searchDates.getDate2().atStartOfDay();
        return orderRepository.findAllByOrderDateBetween(dateTime1, dateTime2);
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
    public long countAllByOrderDateBetween(LocalDateTime date1, LocalDateTime date2) {
        return orderRepository.countAllByOrderDateBetween(date1, date2);
    }

    @Override
    public List<Order> findAllOrdersByDateBetween(LocalDateTime date1, LocalDateTime date2) {
        return orderRepository.findAllByOrderDateBetween(date1, date2);
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
