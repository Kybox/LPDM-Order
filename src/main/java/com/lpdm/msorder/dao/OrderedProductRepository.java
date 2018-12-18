package com.lpdm.msorder.dao;

import com.lpdm.msorder.model.OrderedProduct;
import com.lpdm.msorder.model.OrderedProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {


}
