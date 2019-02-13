package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.order.SearchDates;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.lpdm.msorder.utils.ValueType.EMAIL;
import static com.lpdm.msorder.utils.ValueType.NAME;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

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

    /**
     * Persist an {@link Order} object
     * @param order The {@link Order} object to persist
     * @return The {@link Order} object persisted
     */
    @Override
    public Order saveOrder(Order order) {

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            Product product = proxyService.findProductById(orderedProduct.getProduct().getId());

            orderedProduct.setOrder(order);
            orderedProduct.setProductId(product.getId());
            orderedProduct.setPrice(product.getPrice());
            orderedProduct.setTax(product.getTax());
        }

        return orderRepository.save(order);
    }

    /**
     * Delete an {@link Order} object
     * @param order The {@link Order} object to delete
     */
    @Override
    public void deleteOrder(Order order) {

        orderRepository.delete(order);
    }

    /**
     * Find an {@link Order} object by its id
     * @param id The {@link Order} id
     * @return The {@link Order} object found
     * @throws OrderNotFoundException Thrown if no {@link Order} was found
     */
    @Override
    public Order findOrderById(int id) throws OrderNotFoundException {

        return formatJson.formatOrder(orderRepository
                .findById(id)
                .orElseThrow(OrderNotFoundException::new));
    }

    /**
     * Check if an {@link Order} object exist in the database
     * @param id The {@link Order} id
     * @return True if the {@link Order} exist, otherwise false
     */
    @Override
    public boolean checkIfOrderExist(int id) {

        return orderRepository.findById(id).isPresent();
    }

    /**
     * Find all {@link Order} objects of a {@link User} by its id
     * @param id The {@link User} id
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByCustomerId(int id) {

        return orderRepository.findAllByCustomerId(id);
    }

    /**
     * Find all {@link Order} object with the defined {@link Payment}
     * @param payment The {@link Payment} object in the {@link Order}
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByPayment(Payment payment) {

        List<Order> orderList = orderRepository.findAllByPayment(payment);
        if(orderList.isEmpty()) throw new OrderNotFoundException();

        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects with a pagination
     * @param pageRequest The pagination defined
     * @return The {@link List} of {@link Order} objects found with the pagination limit
     */
    @Override
    public List<Order> findAllOrdersPageable(PageRequest pageRequest) {

        Page<Order> orderPage = orderRepository.findAll(pageRequest);
        List<Order> orderList = orderPage.getContent();

        if(orderList.isEmpty()) throw new OrderNotFoundException();

        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by a {@link User} name or email
     * @param findBy Choose between email or name
     * @param keyword The defined name or email
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByCustomer(String findBy, String keyword) {

        List<Order> orderList;

        switch (findBy){

            case EMAIL:
                orderList = orderRepository.findAllByCustomerId(
                        proxyService.findUserByEmail(keyword).getId());
                break;

            case NAME:
                orderList = orderRepository.findAllByCustomerId(
                        proxyService.findUserByLastName(keyword).getId());
                break;

                default:
                    throw new IllegalArgumentException();
        }

        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} by a {@link User} id and the {@link Order} status
     * @param id The {@link User} id
     * @param status The {@link Order} status
     * @return The {@link List} if {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByCustomerIdAndStatus(int id, Status status) {

        return orderRepository.findAllByCustomerIdAndStatus(id, status);
    }

    /**
     * Find all {@link Order} by the order date between two dates
     * @param searchDates The searchDates object which contains two {@link LocalDateTime} objects
     * @return The {@link List} of {@link Order} found
     */
    @Override
    public List<Order> findAllOrdersBetweenTwoDates(SearchDates searchDates) {

        LocalDateTime dateTime1 = searchDates.getDate1().atStartOfDay();
        LocalDateTime dateTime2 = searchDates.getDate2().atStartOfDay();

        List<Order> orderList = orderRepository.findAllByOrderDateBetween(dateTime1, dateTime2);
        if(orderList.isEmpty()) throw new OrderNotFoundException();

        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by their status with a pagination
     * @param status The {@link Order} status
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByStatusPageable(Status status, PageRequest pageRequest) {

        List<Order> orderList = orderRepository.findAllByStatus(status, pageRequest);
        if(orderList.isEmpty()) throw new OrderNotFoundException();

        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by the {@link User} id
     * and sorted by the order date ascending and with a pagination
     * @param id The {@link Order} id
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByCustomerIdOrderByOrderDateAsc(int id, PageRequest pageRequest) {

        return orderRepository.findAllByCustomerIdOrderByOrderDateAsc(id, pageRequest);
    }

    /**
     * Find all {@link Order} objects by the {@link User} id
     * and sorted by the order date descending and with a pagination
     * @param id The {@link Order} id
     * @param pageRequest The pagination data
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByCustomerIdOrderByOrderDateDesc(int id, PageRequest pageRequest) {

        return orderRepository.findAllByCustomerIdOrderByOrderDateDesc(id, pageRequest);
    }

    /**
     * Count all {@link Order} objects from the database between two dates
     * @param date1 The first {@link LocalDateTime} object
     * @param date2 The second {@link LocalDateTime} object
     * @return The total number of {@link Order} object between this two {@link LocalDateTime}
     */
    @Override
    public long countAllByOrderDateBetween(LocalDateTime date1, LocalDateTime date2) {

        return orderRepository.countAllByOrderDateBetween(date1, date2);
    }

    /**
     * Find all {@link Order} objects between two {@link LocalDateTime} objects
     * @param date1 The first {@link LocalDateTime} object
     * @param date2 The second {@link LocalDateTime} object
     * @return The {@link List} of {@link Order} objects found
     */
    @Override
    public List<Order> findAllOrdersByDateBetween(LocalDateTime date1, LocalDateTime date2) {

        return orderRepository.findAllByOrderDateBetween(date1, date2);
    }

    /**
     * Find the last {@link Order} of a {@link User} id by a {@link Status} id
     * @param customer The {@link User} id
     * @param statusId The {@link Status} id
     * @return The {@link Order} found
     * @throws OrderNotFoundException Thrown if no {@link Order} was found
     */
    @Override
    public Order findLastOrderByCustomerAndStatus(int customer, int statusId) throws OrderNotFoundException {

        Status status = null;
        for(Status s : Status.values())
            if(s.getId() == statusId)
                status = s;

        Optional<Order> optOrder = orderRepository
                .findFirstByCustomerIdAndStatusOrderByOrderDateDesc(customer, status);

        if(!optOrder.isPresent()) throw new OrderNotFoundException();

        return formatJson.formatOrder(optOrder.get());
    }
}
