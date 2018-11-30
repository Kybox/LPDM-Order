package com.lpdm.msorder.dao;

import com.lpdm.msorder.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentDao extends JpaRepository<Payment, UUID> {
}
