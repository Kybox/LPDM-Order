package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
