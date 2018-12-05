package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.dao.PaymentRepository;
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

@RefreshScope
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger log = LogManager.getLogger(this.getClass());

    /**
     * Autowired - Data Access Object
     */
    private final OrderRepository orderDao;
    private final PaymentRepository paymentDao;
    private final OrderedProductRepository orderedProductDao;

    /**
     * Autowired - Controller
     */
    private final UserController userController;
    private final StoreController storeController;
    private final ProductController productController;

    /**
     * Constructr
     * @param storeController
     * @param orderDao
     * @param userController
     * @param productController
     * @param orderedProductDao
     */
    @Autowired
    public OrderController(StoreController storeController, OrderRepository orderDao,
                           UserController userController, ProductController productController,
                           OrderedProductRepository orderedProductDao, PaymentRepository paymentDao) {
        this.storeController = storeController;
        this.orderDao = orderDao;
        this.userController = userController;
        this.productController = productController;
        this.orderedProductDao = orderedProductDao;
        this.paymentDao = paymentDao;
    }

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

    @GetMapping(value = "/find/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Order> findAllByUserId(@PathVariable int id){

        List<Order> orderList = orderDao.findAllByCustomerId(id);

        orderList.forEach(this::formatOrder);

        return orderList;
    }

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

    private Order formatOrder(Order order){

        Optional<Store> optionalStore = storeController.findStoreById(order.getStoreId());
        if(optionalStore.isPresent()) order.setStore(optionalStore.get());
        else {
            log.warn("Store object is null");
            Store store = new Store();
            store.setId(order.getStoreId());
            order.setStore(store);
        }

        Optional<User> optionalUser = userController.findUserById(order.getCustomerId());
        if(optionalUser.isPresent()) order.setCustomer(optionalUser.get());
        else {
            log.warn("User object is null");
            User customer = new User();
            customer.setId(order.getCustomerId());
            order.setCustomer(customer);
        }

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            int productId = orderedProduct.getOrderedProductPK().getProductId();
            log.info("Get product " + productId);
            Optional<Product> optionalProduct = productController.findProductById(productId);

            if(optionalProduct.isPresent()) orderedProduct.setProduct(optionalProduct.get());
            else {
                log.warn("Product is null");
                Product product = new Product();
                product.setId(productId);
                orderedProduct.setProduct(product);
            }
        }

        return order;
    }
}