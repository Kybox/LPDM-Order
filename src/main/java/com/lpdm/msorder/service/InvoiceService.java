package com.lpdm.msorder.service;

import com.lpdm.msorder.model.Invoice;
import com.lpdm.msorder.model.Order;

import java.util.Optional;

public interface InvoiceService {

    Invoice generateNew(Order order);
    Optional<Invoice> getByOrderId(int orderId);
}
