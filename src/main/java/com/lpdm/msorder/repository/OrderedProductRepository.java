package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {

    List<OrderedProduct> findAllByProductId(int id);
    List<OrderedProduct> findAllByOrder(Order order);
    long countAllByOrder(Order order);
    void deleteAllByOrder(Order order);
}
