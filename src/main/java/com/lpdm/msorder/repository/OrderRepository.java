package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.model.Payment;
import com.lpdm.msorder.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(int id);
    List<Order> findAllByPayment(Payment payment);
    List<Order> findAllByCustomer_Email(String email);
    List<Order> findAllByCustomer_LastName(String lastName);
    List<Order> findAllByStatus(Status status, Pageable pageable);
    List<Order> findAllByCustomerIdAndStatus(int userId, Status status);
    List<Order> findAllByCustomerIdOrderByOrderDateAsc(int userId, Pageable pageable);
    List<Order> findAllByCustomerIdOrderByOrderDateDesc(int userId, Pageable pageable);
}
