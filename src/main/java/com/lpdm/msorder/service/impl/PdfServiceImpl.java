package com.lpdm.msorder.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.Invoice;
import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.model.OrderedProduct;
import com.lpdm.msorder.model.User;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.PdfService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PdfServiceImpl implements PdfService {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final String UBUNTU_FONT = new ClassPathResource("/fonts/Ubuntu-M.ttf").getPath();
    private final String PDF_TEMPLATE = new ClassPathResource("/pdf/InvoiceTemplate.pdf").getPath();

    private Font fntUbuntu;
    private BaseFont baseFont;

    private final OrderService orderService;

    @Autowired
    public PdfServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public PdfDocument generatePdf(Invoice invoice, HttpServletResponse servletResponse)
            throws DocumentException, IOException {


        Optional<Order> optOrder = orderService.getById(invoice.getOrderId());
        if(!optOrder.isPresent()) throw new OrderNotFoundException();
        Order order = optOrder.get();


        PdfReader pdfReader = new PdfReader(PDF_TEMPLATE);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, servletResponse.getOutputStream());
        PdfContentByte content = pdfStamper.getOverContent(1);

        fntUbuntu = FontFactory.getFont(UBUNTU_FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
        baseFont = fntUbuntu.getBaseFont();

        content.beginText();

        addReference("20190110172610", content);
        addOrderDate(LocalDateTime.now(), content);

        User user = new User();
        user.setFirstName("Marcel");
        user.setLastName("Badass");
        user.setAddress("18 rue du Faubourg-Saint-Honoré 75000 Paris");
        user.setEmail("marcel@badass.com");
        user.setTel("0605040908");

        addCustomer(user, content);

        addProducts(order.getOrderedProducts(), content);

        pdfStamper.close();
        pdfReader.close();

        return content.getPdfDocument();

    }

    private void addReference(String reference, PdfContentByte content){

        content.setFontAndSize(baseFont, 9);
        content.showTextAligned(PdfContentByte.ALIGN_CENTER,
                reference, 515, 670, 0);
    }

    private void addOrderDate(LocalDateTime dateTime, PdfContentByte content){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");
        content.setFontAndSize(baseFont, 9);
        content.showTextAligned(PdfContentByte.ALIGN_CENTER,
                "Le " + formatter.format(dateTime), 540, 620, 0);
    }

    private void addCustomer(User user, PdfContentByte content){

        content.setFontAndSize(baseFont, 10);
        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                user.getFirstName() + " " + user.getLastName(), 50, 546, 0);

        content.setFontAndSize(baseFont, 9);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                user.getAddress(), 50, 533,0);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                "Tel : " + user.getTel(), 50, 520,0);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                "Email : " + user.getEmail(), 50, 507,0);
    }

    private void addProducts(List<OrderedProduct> productList, PdfContentByte content)
            throws DocumentException {

        PdfPTable table = new PdfPTable(4);
        table.setTotalWidth(new float[]{252.1f, 80.9f, 100.1f, 88.5f});
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBorderWidth(0.1f);

        table.addCell(getCell("Tomate x10", Element.ALIGN_LEFT,0));
        table.addCell(getCell("1", Element.ALIGN_CENTER,0));
        table.addCell(getCell("4.60", Element.ALIGN_CENTER,0));
        table.addCell(getCell("4.60", Element.ALIGN_RIGHT,10));
        table.addCell(getCell("Cabecou", Element.ALIGN_LEFT,0));
        table.addCell(getCell("2", Element.ALIGN_CENTER,0));
        table.addCell(getCell("2.80", Element.ALIGN_CENTER,0));
        table.addCell(getCell("5.60", Element.ALIGN_RIGHT,10));

        table.writeSelectedRows(0, -1, 45.8f, 441f, content);

        PdfPTable tableTotal = new PdfPTable(2);
        tableTotal.setTotalWidth(new float[]{79, 88.5f});
        tableTotal.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableTotal.getDefaultCell().setBorderWidth(Rectangle.NO_BORDER);
        tableTotal.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

        tableTotal.addCell(getCell("Total HT", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("10.20", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("T.V.A. 5.5%", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("0.56", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("Total TTC", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("10.76", Element.ALIGN_RIGHT, 10));

        tableTotal.writeSelectedRows(0, -1, 400, 400, content);
    }

    private PdfPCell getCell(String value, int align, float padding){

        fntUbuntu.setSize(9);
        PdfPCell cell = new PdfPCell(new Phrase(value, fntUbuntu));
        cell.setHorizontalAlignment(align);
        cell.setPaddingRight(padding);
        return cell;
    }
}