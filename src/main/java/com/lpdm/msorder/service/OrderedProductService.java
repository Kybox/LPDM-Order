package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Product;

import java.util.List;

public interface OrderedProductService {

    /**
     * Persist an {@link OrderedProduct} object in the database
     * @param orderedProduct The {@link OrderedProduct} object to persist
     * @return The {@link OrderedProduct} persisted
     */
    OrderedProduct saveOrderedProduct(OrderedProduct orderedProduct);

    /**
     * Find all {@link OrderedProduct} objects by a {@link Product} id
     * @param id Ths {@link Product} id
     * @return A {@link List} of {@link OrderedProduct} objects found
     */
    List<OrderedProduct> findAllOrderedProductsByProductId(int id);

    /**
     * Find all {@link OrderedProduct} objects in an {@link Order} object
     * @param order The {@link Order} object where to find {@link OrderedProduct} objects
     * @return The {@link List} of {@link OrderedProduct} objects found
     */
    List<OrderedProduct> findAllOrderedProductsByOrder(Order order);
}
