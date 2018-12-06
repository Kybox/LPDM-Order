package com.lpdm.msorder.controller;

import com.lpdm.msorder.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RestController
@RefreshScope
public class AbstractController {

    private final Logger log = LogManager.getLogger(this.getClass());

    /**
     * Autowired - Controller
     */
    @Autowired
    private UserController userController;

    @Autowired
    private StoreController storeController;

    @Autowired
    private ProductController productController;

    /**
     * Default constructor
     */

    public AbstractController(){

    }

    /**
     * Format the {@link Order} with correct Json arrays
     * @param order The {@link Order} object to format
     * @return The {@link Order} object formated
     */
    public Order formatOrder(Order order){

        log.info("Storecontroller = " + storeController);
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
