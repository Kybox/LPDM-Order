package com.lpdm.msorder.dao;

import com.lpdm.msorder.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Optional<Invoice> findByOrderId(int orderId);
}
