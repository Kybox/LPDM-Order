package com.lpdm.msorder.controller.json;

import com.lpdm.msorder.model.*;
import com.lpdm.msorder.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormatJson {

    private final ProxyService proxyService;

    @Autowired
    public FormatJson(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * Format the {@link Order} with correct Json arrays
     * @param order The {@link Order} object to format
     * @return The {@link Order} object formated
     */
    public Order formatOrder(Order order){

        Optional<Store> optionalStore = proxyService.findStoreById(order.getStoreId());
        order.setStore(optionalStore.orElse(new Store(order.getStoreId())));

        Optional<User> optionalUser = proxyService.findUserById(order.getCustomerId());
        order.setCustomer(optionalUser.orElse(new User(order.getCustomerId())));

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){
            int productId = orderedProduct.getProductId();
            Optional<Product> optionalProduct = proxyService.findProductById(productId);
            orderedProduct.setProduct(optionalProduct.orElse(new Product(productId, orderedProduct.getPrice())));
        }
        return order;
    }
}
