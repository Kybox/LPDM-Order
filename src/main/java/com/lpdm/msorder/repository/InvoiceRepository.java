package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Optional<Invoice> findByOrderId(int orderId);
    Optional<Invoice> findByReference(String reference);
}
