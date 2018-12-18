package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.dao.PaymentRepository;
import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.exception.DeleteEntityException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.exception.PaymentPersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RefreshScope
@RestController
@RequestMapping("/admin")
public class AdminController extends FormatController {

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
    @PostMapping(value = "payment/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment addNewPayment(@RequestBody Payment payment) {

        try { paymentDao.save(payment); }
        catch (Exception e) { throw new PaymentPersistenceException(); }
        return payment;
    }

    /**
     * Delete a {@link Payment} object
     * @param payment The valid {@link Payment} object to delete
     * @return If it succeeded or not otherwise throw an exception
     */
    @PostMapping(value = "/payment/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deletePayment(@Valid @RequestBody Payment payment) {

        try { paymentDao.delete(payment); }
        catch (Exception e) { throw new DeleteEntityException(); }
        return !paymentDao.findById(payment.getId()).isPresent();
    }

    /**
     * Delete a {@link Order} object
     * @param order The valid {@link Order} object to delete
     * @return If it succeeded or not otherwise throw an exception
     */
    @PostMapping(value = "/order/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteOrder(@Valid @RequestBody Order order){

        try { orderDao.delete(order); }
        catch (Exception e) { throw new DeleteEntityException(); }
        return !orderDao.findById(order.getId()).isPresent();
    }

    /**
     * Get all {@link Order} objects sorted by ASC or DESC
     * @param sort The sort direction
     * @param size The maximum {@link Order} by pages
     * @param page The page number
     * @return The {@link List<Order>} sorted
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/orders/all/date/{sort}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllSortedByDate(@PathVariable String sort,
                                           @RequestParam Optional<Integer> size,
                                           @RequestParam Optional<Integer> page){
        Sort sortDate;
        switch (sort.toLowerCase()){
            case "asc":
                sortDate = new Sort(Sort.Direction.ASC, "orderDate");
                break;
            case "desc":
                sortDate = new Sort(Sort.Direction.DESC, "orderDate");
                break;
            default:
                throw new BadRequestException();
        }

        PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE), sortDate);
        Page<Order> orderPage = orderDao.findAll(pageRequest);
        List<Order> orderList = orderPage.getContent();
        orderList.forEach(this::formatOrder);
        return orderList;
    }

    /**
     * Find {@link Order} by {@link Product} id
     * @param id The product id
     * @return The order {@link List}
     */
    @GetMapping(value = "orders/all/product/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByProductId(@PathVariable int id){

        List<OrderedProduct> orderedProductList = orderedProductDao.findAllByProductId(id);
        List<Optional<Order>> optionalList = new ArrayList<>();
        orderedProductList.forEach(o -> optionalList.add(orderDao.findById(o.getOrder().getId())));
        List<Order> orderList = optionalList.stream().filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        orderList.forEach(this::formatOrder);
        return orderList;
    }

    /**
     * Find {@link Order} by {@link Payment} id
     * @param id Payment id
     * @return The order {@link List}
     */
    @GetMapping(value = "orders/all/payment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByPaymentId(@PathVariable int id){

        Optional<Payment> payment = paymentDao.findById(id);
        List<Order> orderList = null;
        if(payment.isPresent()) {
            orderList = orderDao.findAllByPayment(payment.get());
            orderList.forEach(this::formatOrder);
        }
        return orderList;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/orders/all/status/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByStatus(@PathVariable int id,
                                       @RequestParam(required = false) Optional<Integer> page,
                                       @RequestParam(required = false) Optional<Integer> size){

        Optional<Status> status = Stream.of(Status.values()).filter(s -> s.getId() == id).findFirst();
        if(status.isPresent()){
            PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE));
            List<Order> orderList = orderDao.findAllByStatus(status.get(), pageRequest);

            if(!orderList.isEmpty()){
                orderList.forEach(this::formatOrder);
                return orderList;
            }
            else throw new OrderNotFoundException();
        }
        else throw new OrderNotFoundException();
    }
}
