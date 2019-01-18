package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
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
                            OrderedProductRepository orderedProductRepository, ProxyService proxyService) {

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

    @Override
    public OrderStats getOrderStatsByYear(int year) {

        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = LocalDateTime.of(year, 1, 1,0,0);
        LocalDateTime endStatsDate = LocalDateTime.of(year, 12, startStatsDate.getMonth().maxLength(), 23,59);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusMonths(1)){

            int month = date.getMonthValue();

            LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime end = null;
            LocalDate tempDate = LocalDate.ofYearDay(year, 1);

            // Check leap year for february month
            if(!tempDate.isLeapYear() && date.getMonthValue() == 2)
                end = LocalDateTime.of(year, month, date.getMonth().maxLength() - 1, 23, 59);
            else end = LocalDateTime.of(year, month, date.getMonth().maxLength(), 23, 59);

            long totalOrders = orderRepository.countAllByOrderDateBetween(start, end);

            log.info("Stats " + year + " : Month " + month + " : " + totalOrders + " order(s)");
            log.info("Between " + start + " and " + end);
            orderStats.getDataStats().put(month, totalOrders);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderStatsByYearAndMonth(int year, int month) {


        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = LocalDateTime.of(year, month, 1,0,0);
        LocalDateTime endStatsDate = LocalDateTime.of(year, month,
                startStatsDate.getMonth().maxLength(), 23,59);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusDays(1)){

            LocalDateTime start = LocalDateTime.of(year, month, date.getDayOfMonth(), 0, 0);
            LocalDateTime end = LocalDateTime.of(year, month, date.getDayOfMonth(), 23, 59);

            long totalOrders = orderRepository.countAllByOrderDateBetween(start, end);

            log.info("Stats " + year + " Month " + month + " day " + date.getDayOfMonth() + " : " + totalOrders + " order(s)");
            log.info("Between " + start + " and " + end);
            orderStats.getDataStats().put(date.getDayOfMonth(), totalOrders);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderedProductsStatsByYear(int year) {

        OrderStats orderStats = new OrderStats();

        LocalDateTime startStatsDate = LocalDateTime.of(year, 1, 1,0,0);
        LocalDateTime endStatsDate = LocalDateTime.of(year, 12,
                startStatsDate.getMonth().maxLength(), 23,59);

        for(LocalDateTime date = startStatsDate; date.isBefore(endStatsDate); date = date.plusMonths(1)){

            int month = date.getMonthValue();

            LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime end = null;
            LocalDate tempDate = LocalDate.ofYearDay(year, 1);

            // Check leap year for february month
            if(!tempDate.isLeapYear() && date.getMonthValue() == 2)
                end = LocalDateTime.of(year, month, date.getMonth().maxLength() - 1, 23, 59);
            else end = LocalDateTime.of(year, month, date.getMonth().maxLength(), 23, 59);

            List<Order> orderList = orderRepository.findAllByOrderDateBetween(start, end);

            int totalOrderedProducts = 0;
            for(Order order : orderList) totalOrderedProducts += order.getOrderedProducts().size();

            log.info("Stats " + year + " : Month " + month + " : " + totalOrderedProducts + " product(s)");
            log.info("Between " + start + " and " + end);
            orderStats.getDataStats().put(month, totalOrderedProducts);
        }

        return orderStats;
    }

    @Override
    public OrderStats getOrderedProductsStatsByYearAndCategory(int year) {

        OrderStats orderStats = new OrderStats();
        Map<Object, Object> data = orderStats.getDataStats();

        List<Category> categoryList = proxyService.findAllProductCategories();
        for(Category category : categoryList) data.put(category.getName(), 0);

        LocalDateTime start = LocalDateTime.of(year, 1, 1,0,0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23,59);

        List<Order> orderList = orderRepository.findAllByOrderDateBetween(start, end);
        for(Order order : orderList){

            for(OrderedProduct orderedProduct : order.getOrderedProducts()){

                Product product = proxyService.findProductById(orderedProduct.getProductId());

                for(Category category : categoryList){

                    if(product.getCategory().getId() == category.getId()){

                        int total = ((int) data.get(category.getName())) + 1;
                        data.put(category.getName(), total);
                    }
                }
            }
        }

        return orderStats;
    }
}
