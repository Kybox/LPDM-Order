package com.lpdm.msorder.dao;

import com.lpdm.msorder.model.entity.Order;
import com.lpdm.msorder.model.entity.Payment;
import com.lpdm.msorder.model.entity.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(int id);
    List<Order> findAllByPayment(Payment payment);
    List<Order> findAllByOrderByDateTimeAsc(Pageable pageable);
    List<Order> findAllByOrderByDateTimeDesc(Pageable pageable);
    List<Order> findAllByStatus(Status status, Pageable pageable);
    List<Order> findAllByDateTime(Pageable pageable);
}
