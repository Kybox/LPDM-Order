package com.lpdm.msorder.dao;

import com.lpdm.msorder.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
}
