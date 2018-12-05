package com.lpdm.msorder.dao;

import com.lpdm.msorder.entity.OrderedProduct;
import com.lpdm.msorder.entity.OrderedProductPK;
import com.lpdm.msorder.entity.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductPK> {

    //List<OrderedProduct> findAllByProductId(int id);
    List<OrderedProduct> findAllByOrderedProductPK_ProductId(int id);
    //List<OrderedProduct> findAllByOrderedProductPK_ProductId(int id);
}
