package com.lpdm.msorder.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.InvoiceService;
import com.lpdm.msorder.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RefreshScope
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final FormatJson formatJson;

    @Autowired
    public OrderController(InvoiceService invoiceService,
                           OrderService orderService, FormatJson formatJson) {

        this.invoiceService = invoiceService;
        this.orderService = orderService;
        this.formatJson = formatJson;
    }

    /**
     * Call this method to get an {@link Optional< Order >} by its id
     * @param id The {@link Order} id
     * @return An {@link Optional<Order>} json object
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Optional<Order> getOrderById(@PathVariable int id){

        return Optional.of(orderService.findOrderById(id)
                .map(formatJson::formatOrder)
                .orElseThrow(OrderNotFoundException::new));
    }

    /**
     * This method is called to persist an {@link Order}
     * as well as the list of orderedProduct objects contained in the Order object
     * @param order The {@link Order} object to persist
     * @return An {@link Order} object persisted
     */
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order saveOrder(@Valid @RequestBody Order order){

        if(order.getStore() != null && order.getStore().getId() > 0)
            order.setStoreId(order.getStore().getId());

        if(order.getStatus() == null) {
            log.warn("Status object is null");
            throw new BadRequestException();
        }

        if(order.getCustomer() == null) {
           log.warn("Customer object is null");
           throw new BadRequestException();
        }

        if(order.getCustomer().getId() == 0){
           log.warn("Customer id is null or zero");
           throw new BadRequestException();
        }

        order.setCustomerId(order.getCustomer().getId());

        log.info("Try to save order : " + order.toString());

        orderService.saveOrder(order);

        log.info("Order saved : " + order.toString());

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            orderedProduct.setOrder(order);

            if(orderedProduct.getProduct() != null && orderedProduct.getProduct().getId() > 0){

                orderedProduct.setProductId(orderedProduct.getProduct().getId());
                orderedProduct.setPrice(orderedProduct.getProduct().getPrice());
                orderService.saveOrderedProduct(orderedProduct);
            }
            else throw new BadRequestException();
        }

        if(order.getStatus().equals(Status.PAID) && !invoiceService.isThereAnInvoice(order.getId()))
            invoiceService.generateNew(order);

        order = formatJson.formatOrder(order);

        log.info(order.toString());

        return order;
    }

    /**
     * Find {@link Order} by the user {@link Integer} id
     * @param id The {@link User} {@link Integer} id
     * @return The user ordered {@link List<Order>}
     */
    @GetMapping(value = "/all/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByUserId(@PathVariable int id){

        List<Order> orderList = orderService.findAllOrdersByCustomerId(id);
        if (orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by the {@link User} id and the {@link Status} id
     * @param userId The {@link User} id
     * @param statusId Ths {@link Status} id
     * @return The {@link List<Order>} found otherwise throw an {@link OrderNotFoundException}
     */
    @GetMapping(value = "/all/customer/{userId}/status/{statusId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllUserAndStatus(@PathVariable("userId") int userId,
                                            @PathVariable("statusId") int statusId){

        if(userId == 0 || statusId == 0)
            throw new BadRequestException();

        Optional<Status> status = Stream.of(Status.values())
                .filter(s -> s.getId() == statusId).findFirst();

        if(!status.isPresent())
            throw new BadRequestException();

        List<Order> orderList = orderService.findAllOrdersByCustomerIdAndStatus(userId, status.get());
        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by {@link User} id ordered by ASC or DESC
     * @param id The {@link User} id
     * @param ordered Specify ASC or DESC
     * @param page Sets the number of pages
     * @param size Limit the number of {@link Order} objects returned for each pages
     * @return The {@link List<Order>} found otherwise throw an {@link OrderNotFoundException}
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/all/customer/{id}/date/{ordered}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByUserOrderByDate(@PathVariable("id") int id,
                                             @PathVariable("ordered") String ordered,
                                             @PathVariable(required = false) Optional<Integer> page,
                                             @PathVariable(required = false) Optional<Integer> size){

        if(id == 0) throw new BadRequestException();
        PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(Integer.MAX_VALUE));

        List<Order> orderList = null;
        switch (ordered.toLowerCase()) {
            case "asc": orderList = orderService
                    .findAllOrdersByCustomerIdOrderByOrderDateAsc(id, pageRequest);
                break;

            case "desc": orderList = orderService
                    .findAllOrdersByCustomerIdOrderByOrderDateDesc(id, pageRequest);
                break;

            default:
                throw new BadRequestException();
        }

        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Find all {@link Order} objects by {@link User} id that contains the {@link Product} id
     * @param userId The {@link User} id
     * @param productId The {@link Product} id
     * @return The {@link List<Order>} found otherwise throw an {@link OrderNotFoundException}
     */
    @GetMapping(value = "/all/customer/{userId}/product/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findByUserAndProduct(@PathVariable("userId") int userId,
                                            @PathVariable("productId") int productId){

        if (userId == 0 || productId == 0)
            throw new BadRequestException();

        List<Order> mainOrderList = orderService.findAllOrdersByCustomerId(userId);
        if(mainOrderList.isEmpty()) throw new OrderNotFoundException();

        List<Order> orderList = new ArrayList<>();
        for(Order order : mainOrderList){

            List<OrderedProduct> productList = orderService.findAllOrderedProductsByOrder(order);
            for(OrderedProduct orderedProduct : productList){
                if(orderedProduct.getProductId() == productId){
                    orderList.add(order);
                    break;
                }
            }
        }

        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(formatJson::formatOrder);
        return orderList;
    }

    /**
     * Get all {@link Payment} recorded in the database
     * @return The {@link List<Payment>}
     */
    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Payment> getPaymentList(){
        return orderService.findAllPayments();
    }

    /**
     *  Generate an invoice for a paid order
     * @param id The {@link Order} id
     * @param response The {@link HttpServletResponse} object
     * @return The PDF Document
     */
    @GetMapping(value = "/{id}/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public PdfDocument getInvoiceByOrderId(@PathVariable(name = "id") int id,
                                           HttpServletResponse response)
            throws IOException, DocumentException {

        Optional<Order> optOrder = orderService.findOrderById(id);
        if(!optOrder.isPresent()) throw new BadRequestException();

        Optional<Invoice> optInvoice = invoiceService.getByOrderId(optOrder.get().getId());
        if(!optInvoice.isPresent()) {
            if(optOrder.get().getStatus().getId() >= Status.PAID.getId()) {
                Invoice invoice = invoiceService.generateNew(optOrder.get());
                return invoiceService.generatePdf(invoice, response);
            }
            else throw new BadRequestException();
        }

        return invoiceService.generatePdf(optInvoice.get(), response);
    }
}