package com.lpdm.msorder.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.exception.CouponNotFoundException;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.lpdm.msorder.utils.ValueType.ORDERS_PATH;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RestController
@RequestMapping(ORDERS_PATH)
@Api(tags = {"Order Rest API"})
public class OrderController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final FormatJson formatJson;
    private final DeliveryService deliveryService;
    private final CouponService couponService;
    private final OrderedProductService orderedProductService;

    @Autowired
    public OrderController(InvoiceService invoiceService,
                           OrderService orderService, FormatJson formatJson,
                           DeliveryService deliveryService,
                           CouponService couponService,
                           OrderedProductService orderedProductService) {

        this.invoiceService = invoiceService;
        this.orderService = orderService;
        this.formatJson = formatJson;
        this.deliveryService = deliveryService;
        this.couponService = couponService;
        this.orderedProductService = orderedProductService;
    }

    /**
     * Call this method to get an {@link Order} by its id
     * @param id The {@link Order} id
     * @return An {@link Order} json object
     */
    @ApiOperation(
            value = "Get an order by its id",
            notes = "Several Microservice are asked to construct the response " +
                    "with all the attributes nécesssaires")
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order getOrderById(@PathVariable int id){

        return orderService.findOrderById(id);
    }

    /**
     * This method is called to persist an {@link Order}
     * as well as the list of orderedProduct objects contained in the Order object
     * @param order The {@link Order} object to persist
     * @return An {@link Order} object persisted
     */
    @ApiOperation(
            value = "Persist a valid Order object",
            notes = "Several checks are performed before the order's persistence")
    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
                orderedProduct.setTax(orderedProduct.getProduct().getTax());
                orderedProductService.saveOrderedProduct(orderedProduct);
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
    @ApiOperation(
            value = "Find all orders of the customer defined by its id",
            notes = "This request must be controlled and limited to the customer or an admin")
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
    @ApiOperation(
            value = "Find all orders of the customer defined by its id with a defined status",
            notes = "This request must be controlled and limited to the customer or an admin")
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
    @ApiOperation(
            value = "Find all the customer orders defined by its identifier " +
                    "and whose result is sorted by date",
            notes = "The sort by date is defined by the keywords 'asc' or 'desc' " +
                    "and it is possible to obtain a pagination")
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(value = "/all/customer/{id}/date/{ordered}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @ApiOperation(
            value = "Find all the customer orders defined by its identifier " +
                    "who ordered a product defined in the request")
    @GetMapping(value = "/all/customer/{userId}/product/{productId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findByUserAndProduct(@PathVariable("userId") int userId,
                                            @PathVariable("productId") int productId){

        if (userId == 0 || productId == 0)
            throw new BadRequestException();

        List<Order> mainOrderList = orderService.findAllOrdersByCustomerId(userId);
        if(mainOrderList.isEmpty()) throw new OrderNotFoundException();

        List<Order> orderList = new ArrayList<>();
        for(Order order : mainOrderList){

            List<OrderedProduct> productList = orderedProductService.findAllOrderedProductsByOrder(order);
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
     * Generate an invoice for a paid order
     * @param id The {@link Order} id
     * @param response The {@link HttpServletResponse} object
     * @return The PDF Document
     */
    @ApiOperation(
            value = "Get an invoice PDF file referring to an order id",
            notes = "A PDF file is returned only if the command has a status equal to or greater than PAID")
    @GetMapping(value = "/{id}/invoice",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public PdfDocument getInvoiceByOrderId(@PathVariable(name = "id") int id,
                                           HttpServletResponse response)
            throws IOException, DocumentException {

        Order order = orderService.findOrderById(id);

        if(order.getStatus().getId() >= Status.PAID.getId()) {
            Invoice invoice = invoiceService.generateNew(order);
            return invoiceService.generatePdf(invoice, response);
        }

        return invoiceService.generatePdf(invoiceService.getByOrderId(order.getId()), response);
    }

    /**
     * Find all delivery methods
     * @return The {@link List<Delivery>} object
     */
    @ApiOperation(
            value = "Get all delivery methods available in database")
    @GetMapping(value = "/delivery/all",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Delivery> getAllDeliveryMethods(){

        return deliveryService.findAllDeliveryMethods();
    }

    /**
     * Check if the {@link Coupon} code is valid
     * @param code The {@link Coupon} code to check
     * @return The {@link Coupon} object if the code is valid
     * @throws CouponNotFoundException Thrown if the {@link Coupon} code is not valid
     */
    @GetMapping(value = "/coupon/check")
    public Coupon checkCouponCode(@RequestParam String code) throws CouponNotFoundException{

        return couponService.checkCouponCode(code);
    }
}