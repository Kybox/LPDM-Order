package com.lpdm.msorder.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfStamper;
import com.lpdm.msorder.model.Invoice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PdfService {

    PdfDocument generatePdf(Invoice invoice, HttpServletResponse servletResponse) throws IOException, DocumentException;
}
