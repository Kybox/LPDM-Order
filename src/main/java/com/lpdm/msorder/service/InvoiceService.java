package com.lpdm.msorder.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfStamper;
import com.lpdm.msorder.exception.InvoiceNotFoundException;
import com.lpdm.msorder.model.order.Invoice;
import com.lpdm.msorder.model.order.Order;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface InvoiceService {

    /**
     * Generate and persist an new {@link Invoice} object from the {@link Order}
     * @param order The {@link Order} that is referenced in the invoice
     * @return The {@link Invoice} saved
     */
    Invoice generateNew(Order order);

    /**
     * Get an {@link Invoice} object from the {@link Order} id
     * @param orderId The {@link Order} id
     * @return The {@link Invoice} object
     * @throws InvoiceNotFoundException Thrown if no {@link Invoice} was found
     */
    Invoice getByOrderId(int orderId) throws InvoiceNotFoundException;

    /**
     * Check if there is an {@link Invoice} linked to an {@link Order} ID
     * @param orderId The {@link Order} id
     * @return True if there is an invoice for this order, otherwise false
     */
    boolean isThereAnInvoice(int orderId);

    /**
     * Find an {@link Invoice} by its reference
     * @param reference The {@link Invoice} reference
     * @return An {@link Invoice} object
     * @throws InvoiceNotFoundException Thrown if no {@link Invoice} was found
     */
    Invoice findInvoiceByReference(String reference) throws InvoiceNotFoundException;

    /**
     * Generate a PDF file from the {@link Invoice} object
     * @param invoice The {@link Invoice} object
     * @param servletResponse The {@link HttpServletResponse} where generate the PDF file
     * @return A {@link PdfDocument} that will generate the PDF file
     * @throws IOException Thrown if the PDF template file is not found
     * @throws DocumentException Thrown if the {@link PdfStamper} not recognize the servlet output stream
     */
    PdfDocument generatePdf(Invoice invoice, HttpServletResponse servletResponse) throws IOException, DocumentException;
}
