package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.dao.PaymentRepository;
import com.lpdm.msorder.entity.*;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.exception.OrderPersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RefreshScope
@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final OrderRepository orderDao;
    private final PaymentRepository paymentDao;
    private final OrderedProductRepository orderedProductDao;

    @Autowired
    public OrderController(OrderRepository orderDao, OrderedProductRepository orderedProductDao, PaymentRepository paymentDao) {
        this.orderDao = orderDao;
        this.orderedProductDao = orderedProductDao;
        this.paymentDao = paymentDao;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order getOrderById(@PathVariable int id){

        return orderDao.findById(id)
                .map(this::formatOrder)
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * This method is called to add a new command.
     * The method records the Order object in the database
     * as well as the list of orderedProduct objects contained in the Order object
     * @param order Order class
     * @return Order class
     */
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order saveOrder(@Valid @RequestBody Order order){

        order.getOrderedProducts().forEach(orderedProductDao::save);
        try { orderDao.save(order); }
        catch (Exception e) { throw new OrderPersistenceException(); }
        return order;
    }

    /**
     * Find {@link Order} by the user {@link Integer} id
     * @param id The {@link User} {@link Integer} id
     * @return The user ordered {@link List}
     */
    @GetMapping(value = "/by/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByUserId(@PathVariable int id){

        List<Order> orderList = orderDao.findAllByCustomerId(id);
        if (orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(this::formatOrder);
        return orderList;
    }

    /**
     * Get all {@link Payment} recorded
     * @return The {@link List<Payment>}
     */
    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Payment> getPaymentList(){
        return paymentDao.findAll();
    }
}