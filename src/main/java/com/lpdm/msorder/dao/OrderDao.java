package com.lpdm.msorder.dao;

import com.lpdm.msorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
