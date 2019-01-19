package com.lpdm.msorder.controller;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.exception.DeleteEntityException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.exception.PaymentPersistenceException;
import com.lpdm.msorder.service.InvoiceService;
import com.lpdm.msorder.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final FormatJson formatJson;

    @Autowired
    public AdminController(FormatJson formatJson,
                           OrderService orderService,
                           InvoiceService invoiceService) {

        this.formatJson = formatJson;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    /**
     * Add a new {@link Payment}
     * @param payment The new {@link Payment} object
     * @return The new {@link Payment} added
     */
    @PutMapping(value = "/payment/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Payment addNewPayment(@Valid @RequestBody Payment payment) {

        try { payment = orderService.savePayment(payment); }
        catch (Exception e) { throw new PaymentPersistenceException(); }
        return payment;
    }

    /**
     * Delete a {@link Payment} object
     * @param payment The valid {@link Payment} object to delete
     * @return If it succeeded or not otherwise throw an exception
     */
    @DeleteMapping(value = "/payment/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deletePayment(@Valid @RequestBody Payment payment) {

        try { orderService.deletePayment(payment); }
        catch (Exception e) { throw new DeleteEntityException(); }
        return !orderService.findPaymentById(payment.getId()).isPresent();
    }

    /**
     * Delete a {@link Order} object
     * @param order The valid {@link Order} object to delete
     * @return If it succeeded or not otherwise throw an exception
     */
    @DeleteMapping(value = "/order/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean deleteOrder(@Valid @RequestBody Order order){

        try { orderService.deleteOrder(order); }
        catch (Exception e) { throw new DeleteEntityException(); }
        return !orderService.findOrderById(order.getId()).isPresent();
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
        Page<Order> orderPage = orderService.findAllOrdersPageable(pageRequest);
        List<Order> orderList = orderPage.getContent();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find {@link Order} by {@link Product} id
     * @param id The product id
     * @return The order {@link List}
     */
    @GetMapping(value = "/orders/all/product/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByProductId(@PathVariable int id){

        List<OrderedProduct> orderedProductList = orderService.findAllOrderedProductsByProductId(id);
        List<Optional<Order>> optionalList = new ArrayList<>();
        orderedProductList.forEach(o -> optionalList.add(orderService.findOrderById(o.getOrder().getId())));
        List<Order> orderList = optionalList.stream().filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find {@link Order} by {@link Payment} id
     * @param id Payment id
     * @return The order {@link List}
     */
    @GetMapping(value = "/orders/all/payment/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByPaymentId(@PathVariable int id){

        Optional<Payment> payment = orderService.findPaymentById(id);
        List<Order> orderList = null;
        if(payment.isPresent()) {
            orderList = orderService.findAllOrdersByPayment(payment.get());
            orderList.forEach(formatJson::formatOrder);
        }
        return orderList;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/orders/all/status/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByStatus(@PathVariable int id,
                                       @RequestParam(required = false) Optional<Integer> page,
                                       @RequestParam(required = false) Optional<Integer> size){

        Optional<Status> status = Stream.of(Status.values()).filter(s -> s.getId() == id).findFirst();
        if(status.isPresent()){
            PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE));
            List<Order> orderList = orderService.findAllOrdersByStatusPageable(status.get(), pageRequest);

            if(!orderList.isEmpty()){
                orderList.forEach(formatJson::formatOrder);
                return orderList;
            }
            else throw new OrderNotFoundException();
        }
        else throw new OrderNotFoundException();
    }

    @GetMapping(value = "orders/invoice/{ref}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order findByInvoiceRef(@PathVariable String ref){

        Optional<Invoice> optInvoice = invoiceService.findInvoiceByReference(ref);
        if(!optInvoice.isPresent()) throw new OrderNotFoundException();

        Optional<Order> optOrder = orderService.findOrderById(optInvoice.get().getOrderId());
        if(!optOrder.isPresent()) throw new OrderNotFoundException();

        Order order = optOrder.get();
        order = formatJson.formatOrder(order);

        return order;
    }

    @GetMapping(value = "orders/all/customer/email/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByEmail(@PathVariable String email){

        List<Order> orderList = orderService.findAllOrdersByCustomerEmail(email);
        if(!orderList.isEmpty()) orderList.forEach(formatJson::formatOrder);
        else throw new OrderNotFoundException();
        return orderList;
    }

    @GetMapping(value = "orders/all/customer/name/{name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByName(@PathVariable String name){

        List<Order> orderList = orderService.findAllOrdersByCustomerLastName(name);
        if(orderList.isEmpty()) throw new OrderNotFoundException();
        return orderList;
    }

    @PostMapping(value = "/orders/dates/between",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByDateBetween(@Valid @RequestBody SearchDates searchDates){

        List<Order> orderList = orderService.findAllOrdersBetweenTwoDates(searchDates);
        for(Order order : orderList) log.info("Order : " + order);
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    @GetMapping(value = "/orders/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderStatsByYear(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = orderService.getOrderStatsByYear(year);
        if(orderStats.getDataStats().isEmpty()) throw  new OrderNotFoundException();
        return orderStats;
    }

    @GetMapping(value = "/orders/stats/year/{year}/month/{month}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderStatsByYearAndMonth(@PathVariable Integer year,
                                                  @PathVariable Integer month){

        if(year == null || month == null) throw new BadRequestException();
        OrderStats orderStats = orderService.getOrderStatsByYearAndMonth(year, month);
        if(orderStats.getDataStats().isEmpty()) throw  new OrderNotFoundException();
        return orderStats;
    }

    @GetMapping(value = "/orderedproducts/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderedProductsByYear(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = orderService.getOrderedProductsStatsByYear(year);
        if(orderStats.getDataStats().isEmpty()) throw new OrderNotFoundException();
        return orderStats;
    }

    @GetMapping(value = "/orderedproducts/stats/year/{year}/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OrderStats getOrderedProductsByYearAndCategories(@PathVariable Integer year){

        if(year == null) throw new BadRequestException();
        OrderStats orderStats = orderService.getOrderedProductsStatsByYearAndCategory(year);
        if(orderStats.getDataStats().isEmpty()) throw new OrderNotFoundException();
        return orderStats;
    }
}
