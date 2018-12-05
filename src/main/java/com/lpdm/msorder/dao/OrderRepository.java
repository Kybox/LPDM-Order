package com.lpdm.msorder.dao;

import com.lpdm.msorder.entity.Order;
import com.lpdm.msorder.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(int id);
    List<Order> findAllByPayment(Payment payment);
}
