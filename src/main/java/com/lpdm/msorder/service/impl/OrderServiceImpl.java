package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.controller.json.FormatJson;
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

import static com.lpdm.msorder.utils.ValueType.BY_EMAIL;
import static com.lpdm.msorder.utils.ValueType.BY_NAME;

@Service
public class OrderServiceImpl implements OrderService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final FormatJson formatJson;
    private final ProxyService proxyService;
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderedProductRepository orderedProductRepository,
                            ProxyService proxyService, FormatJson formatJson) {

        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.proxyService = proxyService;
        this.formatJson = formatJson;
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
    public Order findOrderById(int id) throws OrderNotFoundException {

        return formatJson.formatOrder(orderRepository
                .findById(id)
                .orElseThrow(OrderNotFoundException::new));
    }

    @Override
    public boolean checkIfOrderExist(int id) {
        return orderRepository.findById(id).isPresent();
    }

    @Override
    public List<Order> findAllOrdersByCustomerId(int id) {
        return orderRepository.findAllByCustomerId(id);
    }

    @Override
    public List<Order> findAllOrdersByCustomerEmail(String email) {
        return orderRepository.findAllByCustomerId(proxyService.findUserByEmail(email).getId());
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
        return orderRepository.findAllByCustomerId(proxyService.findUserByLastName(lastName).getId());
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


}
