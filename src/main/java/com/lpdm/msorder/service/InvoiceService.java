package com.lpdm.msorder.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.lpdm.msorder.model.Invoice;
import com.lpdm.msorder.model.Order;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface InvoiceService {

    /**
     * Invoice
     */
    Invoice generateNew(Order order);
    Optional<Invoice> getByOrderId(int orderId);
    boolean isThereAnInvoice(int orderId);

    /**
     * IText PDF Document
     */
    PdfDocument generatePdf(Invoice invoice, HttpServletResponse servletResponse) throws IOException, DocumentException;
}
