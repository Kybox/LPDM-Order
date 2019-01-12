package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.dao.InvoiceRepository;
import com.lpdm.msorder.model.Invoice;
import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice generateNew(Order order) {

        Invoice invoice = new Invoice();
        invoice.setOrderId(order.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        invoice.setReference(formatter.format(LocalDateTime.now()));

        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> getByOrderId(int orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }
}
