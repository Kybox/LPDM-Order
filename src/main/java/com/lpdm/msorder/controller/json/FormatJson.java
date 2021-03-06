package com.lpdm.msorder.controller.json;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.store.Store;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.DeliveryService;
import com.lpdm.msorder.service.ProxyService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Component
public class FormatJson {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProxyService proxyService;

    @Autowired
    private DeliveryService deliveryService;

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


        Store store;
        try {
            store = proxyService.findStoreById(order.getStoreId());
            order.setStore(store);
        }
        catch (FeignException e){
            log.warn(e.getMessage());
            order.setStore(new Store(order.getStoreId()));
        }

        if(order.getDelivery() != null && order.getDelivery().getId() > 0){
            order.setDelivery(deliveryService.findDeliveryMethodById(order.getDelivery().getId()));
        }

        User user = proxyService.findUserById(order.getCustomerId());
        order.setCustomer(user);

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){
            int productId = orderedProduct.getProductId();
            try{
                Product product = proxyService.findProductById(productId);
                if(product != null) orderedProduct.setProduct(product);
                else orderedProduct.setProduct(new Product(productId, orderedProduct.getPrice()));
            }
            catch (FeignException e) {
                log.warn(e.getMessage());
            }
        }
        return order;
    }
}
