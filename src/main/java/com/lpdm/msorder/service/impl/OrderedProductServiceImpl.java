package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.service.OrderedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class OrderedProductServiceImpl implements OrderedProductService {

    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public OrderedProductServiceImpl(OrderedProductRepository orderedProductRepository) {
        this.orderedProductRepository = orderedProductRepository;
    }

    /**
     * Persist an {@link OrderedProduct} object in the database
     * @param orderedProduct The {@link OrderedProduct} object to persist
     * @return The {@link OrderedProduct} persisted
     */
    @Override
    public OrderedProduct saveOrderedProduct(OrderedProduct orderedProduct) {

        return orderedProductRepository.save(orderedProduct);
    }

    /**
     * Find all {@link OrderedProduct} objects by a {@link Product} id
     * @param id Ths {@link Product} id
     * @return A {@link List} of {@link OrderedProduct} objects found
     */
    @Override
    public List<OrderedProduct> findAllOrderedProductsByProductId(int id) {

        return orderedProductRepository.findAllByProductId(id);
    }

    /**
     * Find all {@link OrderedProduct} objects in an {@link Order} object
     * @param order The {@link Order} object where to find {@link OrderedProduct} objects
     * @return The {@link List} of {@link OrderedProduct} objects found
     */
    @Override
    public List<OrderedProduct> findAllOrderedProductsByOrder(Order order) {

        return orderedProductRepository.findAllByOrder(order);
    }
}
