package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.order.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(int id);
    List<Order> findAllByPayment(Payment payment);
    List<Order> findAllByStatus(Status status, Pageable pageable);
    List<Order> findAllByCustomerIdAndStatus(int userId, Status status);
    List<Order> findAllByCustomerIdOrderByOrderDateAsc(int userId, Pageable pageable);
    List<Order> findAllByCustomerIdOrderByOrderDateDesc(int userId, Pageable pageable);
    long countAllByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findAllByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}
