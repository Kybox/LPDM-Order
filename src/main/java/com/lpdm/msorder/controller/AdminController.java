package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.PaymentDao;
import com.lpdm.msorder.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RefreshScope
@RestController
@RequestMapping("/admin")
public class AdminController extends AbstractController{

    private final Logger log = LogManager.getLogger(this.getClass());

    private final PaymentDao paymentDao;

    @Autowired
    public AdminController(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * Add a new {@link Payment}
     * @param payment The new {@link Payment} object
     * @return The new {@link Payment} added
     */
    @PostMapping(value = "/addNewPayment", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment addNewPayment(@Valid @RequestBody Payment payment){
        return paymentDao.save(payment);
    }

    @PostMapping(value = "/delete/order/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteOrder(@RequestBody Order order){
        int id = order.getId();
        orderDao.delete(order);
        Optional<Order> optOrder = orderDao.findById(id);
        return !optOrder.isPresent();
    }

    /**
     * Find {@link Order} by {@link Product} id
     * @param id The product id
     * @return The order {@link List}
     */
    @GetMapping(value = "/find/product/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByProductId(@PathVariable int id){

        List<OrderedProduct> orderedProductList = orderedProductDao
                .findAllByOrderedProductPK_ProductId(id);

        List<Optional<Order>> optionalList = new ArrayList<>();

        orderedProductList.stream().filter(s -> s.getOrder().getId() == id)
                .forEach(o -> optionalList.add(orderDao.findById(o.getOrder().getId())));

        List<Order> orderList = optionalList.stream().filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        orderList.stream().forEach(this::formatOrder);
        return orderList;
    }

    /**
     * Find {@link Order} by {@link Payment} id
     * @param id Payment id
     * @return The order {@link List}
     */
    @GetMapping(value = "/find/payment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByPaymentId(@PathVariable int id){

        Optional<Payment> payment = paymentDao.findById(id);
        List<Order> orderList = null;
        if(payment.isPresent()) {
            orderList = orderDao.findAllByPayment(payment.get());
            orderList.stream().forEach(this::formatOrder);
        }
        return orderList;
    }
}
