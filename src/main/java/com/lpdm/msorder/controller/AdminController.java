package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.dao.PaymentRepository;
import com.lpdm.msorder.entity.*;
import com.lpdm.msorder.exception.OrderPersistenceException;
import com.lpdm.msorder.exception.PaymentPersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    private final OrderRepository orderDao;
    private final PaymentRepository paymentDao;
    private final OrderedProductRepository orderedProductDao;

    @Autowired
    public AdminController(PaymentRepository paymentDao, OrderRepository orderDao,
                           OrderedProductRepository orderedProductDao) {
        this.orderDao = orderDao;
        this.paymentDao = paymentDao;
        this.orderedProductDao = orderedProductDao;
    }

    /**
     * Add a new {@link Payment}
     * @param payment The new {@link Payment} object
     * @return The new {@link Payment} added
     */
    @PostMapping(value = "/add/payment", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment addNewPayment(@RequestBody Payment payment) {

        try { paymentDao.save(payment); }
        catch (Exception e) { throw new PaymentPersistenceException(); }
        return payment;
    }

    @PostMapping(value = "order/delete/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteOrder(@RequestBody Order order, @PathVariable int id){
        if(id == order.getId()){
            orderDao.delete(order);
            Optional<Order> optOrder = orderDao.findById(id);
            return !optOrder.isPresent();
        }
        else throw new OrderPersistenceException();
    }

    /**
     * Find {@link Order} by {@link Product} id
     * @param id The product id
     * @return The order {@link List}
     */
    @GetMapping(value = "orders/by/product/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(value = "orders/by/payment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
