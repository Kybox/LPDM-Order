package com.lpdm.msorder.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfStamper;
import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.dao.PaymentRepository;
import com.lpdm.msorder.exception.BadRequestException;
import com.lpdm.msorder.model.*;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.service.InvoiceService;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.PdfService;
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
public class OrderController extends FormatController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final OrderRepository orderDao;
    private final PaymentRepository paymentDao;
    private final OrderedProductRepository orderedProductDao;
    private final PdfService pdfService;
    private final InvoiceService invoiceService;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderDao, OrderedProductRepository orderedProductDao,
                           PaymentRepository paymentDao, PdfService pdfService,
                           InvoiceService invoiceService, OrderService orderService) {
        this.orderDao = orderDao;
        this.orderedProductDao = orderedProductDao;
        this.paymentDao = paymentDao;
        this.pdfService = pdfService;
        this.invoiceService = invoiceService;
        this.orderService = orderService;
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

        if(order.getStore() != null && order.getStore().getId() > 0)
            order.setStoreId(order.getStore().getId());

        if(order.getCustomerId() > 0)
            order.setCustomerId(order.getCustomerId());
        else throw new BadRequestException();

        orderDao.save(order);

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            orderedProduct.setOrder(order);

            if(orderedProduct.getProduct() != null && orderedProduct.getProduct().getId() > 0){

                orderedProduct.setProductId(orderedProduct.getProduct().getId());
                orderedProduct.setPrice(orderedProduct.getProduct().getPrice());
                orderedProductDao.save(orderedProduct);
            }
            else throw new BadRequestException();
        }

        if(order.getStatus().equals(Status.PAID)) invoiceService.generateNew(order);

        order = formatOrder(order);

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

        List<Order> orderList = orderDao.findAllByCustomerId(id);
        if (orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(this::formatOrder);
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

        List<Order> orderList = orderDao.findAllByCustomerIdAndStatus(userId, status.get());
        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(this::formatOrder);
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
            case "asc": orderList = orderDao.findAllByCustomerIdOrderByOrderDateAsc(id, pageRequest);
                break;
            case "desc": orderList = orderDao.findAllByCustomerIdOrderByOrderDateDesc(id, pageRequest);
                break;
            default:
                throw new BadRequestException();
        }

        if(orderList.isEmpty()) throw new OrderNotFoundException();
        orderList.forEach(this::formatOrder);
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

        List<Order> mainOrderList = orderDao.findAllByCustomerId(userId);
        if(mainOrderList.isEmpty()) throw new OrderNotFoundException();

        List<Order> orderList = new ArrayList<>();
        for(Order order : mainOrderList){

            List<OrderedProduct> productList = orderedProductDao.findAllByOrder(order);
            for(OrderedProduct orderedProduct : productList){
                if(orderedProduct.getProductId() == productId){
                    orderList.add(order);
                    break;
                }
            }
        }

        if(orderList.isEmpty()) throw new OrderNotFoundException();
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

    @GetMapping(value = "/orders/{id}/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public PdfDocument getInvoiceByOrderId(@PathVariable(name = "id") int id,
                                           HttpServletResponse response)
            throws IOException, DocumentException {

        Optional<Order> optOrder = orderService.getById(id);
        if(!optOrder.isPresent()) throw new BadRequestException();

        Optional<Invoice> optInvoice = invoiceService.getByOrderId(optOrder.get().getId());
        if(!optInvoice.isPresent()) throw new BadRequestException();

        return pdfService.generatePdf(optInvoice.get(), response);
    }
}