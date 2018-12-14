package com.lpdm.msorder.dao;

import com.lpdm.msorder.model.entity.OrderedProduct;
import com.lpdm.msorder.model.entity.OrderedProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductPK> {

    //List<OrderedProduct> findAllByProductId(int id);
    List<OrderedProduct> findAllByOrderedProductPK_ProductId(int id);
    //List<OrderedProduct> findAllByOrderedProductPK_ProductId(int id);
}
