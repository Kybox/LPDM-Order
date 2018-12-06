package com.lpdm.msorder.controller;

import com.lpdm.msorder.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RefreshScope
@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController {

    private final Logger log = LogManager.getLogger(this.getClass());

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order getOrderById(@PathVariable int id){

        Optional<Order> optionalOrder = orderDao.findById(id);

        return optionalOrder.map(this::formatOrder).orElse(null);
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

        return orderDao.save(order);
    }

    /**
     * Find {@link Order} by the user {@link Integer} id
     * @param id The {@link User} {@link Integer} id
     * @return The user ordered {@link List}
     */
    @GetMapping(value = "/find/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByUserId(@PathVariable int id){

        List<Order> orderList = orderDao.findAllByCustomerId(id);

        orderList.forEach(this::formatOrder);

        return orderList;
    }
}