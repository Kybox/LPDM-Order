package com.lpdm.msorder.controller;

import com.lpdm.msorder.dao.OrderDao;
import com.lpdm.msorder.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final OrderDao orderDao;
    private final UserController userController;
    private final StoreController storeController;
    private final ProductController productController;

    @Autowired
    public OrderController(StoreController storeController, OrderDao orderDao,
                           UserController userController, ProductController productController) {
        this.storeController = storeController;
        this.orderDao = orderDao;
        this.userController = userController;
        this.productController = productController;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order getOrderById(@PathVariable int id){

        Optional<Order> optionalOrder = orderDao.findById(id);

        if(optionalOrder.isPresent()){

            Order order = optionalOrder.get();

            Optional<Store> optionalStore = storeController.findStoreById(order.getStoreId());
            if(optionalStore.isPresent()) order.setStore(optionalStore.get());
            else log.warn("Store object is null");

            Optional<User> optionalUser = userController.findUserById(order.getCustomerId());
            if(optionalUser.isPresent()) order.setCustomer(optionalUser.get());
            else log.warn("User object is null");

            for(OrderedProduct orderedProduct : order.getOrderedProducts()){

                int productId = orderedProduct.getOrderedProductPK().getProductId();
                log.info("Get product " + productId);
                Optional<Product> optionalProduct = productController.findProductById(productId);

                if(optionalProduct.isPresent()) orderedProduct.setProduct(optionalProduct.get());
                else log.warn("Product is null");
            }
            return order;
        }
        else return null;
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Order saveOrder(@Valid @RequestBody Order order){

        return orderDao.save(order);
    }
}