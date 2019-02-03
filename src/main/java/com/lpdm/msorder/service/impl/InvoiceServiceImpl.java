package com.lpdm.msorder.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.lpdm.msorder.exception.InvoiceNotFoundException;
import com.lpdm.msorder.exception.UserMalFormedException;
import com.lpdm.msorder.model.order.Invoice;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.repository.InvoiceRepository;
import com.lpdm.msorder.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final float CELL_HEIGHT = 16;
    private final DecimalFormat PRICE_FORMAT = new DecimalFormat("####0.00");
    private final String UBUNTU_FONT = new ClassPathResource("/fonts/Ubuntu-M.ttf").getPath();
    private final String PDF_TEMPLATE = new ClassPathResource("/pdf/InvoiceTemplate.pdf").getPath();

    private Font fntUbuntu;
    private BaseFont baseFont;

    private final OrderService orderService;
    private final InvoiceRepository invoiceRepository;
    private final ProxyService proxyService;
    private final OrderedProductService orderedProductService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              OrderService orderService,
                              ProxyService proxyService,
                              OrderedProductService orderedProductService) {

        this.invoiceRepository = invoiceRepository;
        this.orderService = orderService;
        this.proxyService = proxyService;
        this.orderedProductService = orderedProductService;
    }

    /**
     * Generate and persist an new {@link Invoice} object from the {@link Order}
     * @param order The {@link Order} that is referenced in the invoice
     * @return The {@link Invoice} saved
     */
    @Override
    public Invoice generateNew(Order order) {

        Invoice invoice = new Invoice();
        invoice.setOrderId(order.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        invoice.setReference(formatter.format(order.getOrderDate()));

        return invoiceRepository.save(invoice);
    }

    /**
     * Get an {@link Invoice} object from the {@link Order} id
     * @param orderId The {@link Order} id
     * @return The {@link Invoice} object
     * @throws InvoiceNotFoundException Thrown if no {@link Invoice} was found
     */
    @Override
    public Invoice getByOrderId(int orderId) throws InvoiceNotFoundException {

        return invoiceRepository.findByOrderId(orderId).orElseThrow(InvoiceNotFoundException::new);
    }

    /**
     * Check if there is an {@link Invoice} linked to an {@link Order} ID
     * @param orderId The {@link Order} id
     * @return True if there is an invoice for this order, otherwise false
     */
    @Override
    public boolean isThereAnInvoice(int orderId) {

        return invoiceRepository.findByOrderId(orderId).isPresent();
    }

    /**
     * Find an {@link Invoice} by its reference
     * @param reference The {@link Invoice} reference
     * @return An {@link Invoice} object
     * @throws InvoiceNotFoundException Thrown if no {@link Invoice} was found
     */
    @Override
    public Invoice findInvoiceByReference(String reference) throws InvoiceNotFoundException {

        return invoiceRepository.findByReference(reference).orElseThrow(InvoiceNotFoundException::new);
    }

    /**
     * Generate a PDF file from the {@link Invoice} object
     * @param invoice The {@link Invoice} object
     * @param servletResponse The {@link HttpServletResponse} where generate the PDF file
     * @return A {@link PdfDocument} that will generate the PDF file
     * @throws IOException Thrown if the PDF template file is not found
     * @throws DocumentException Thrown if the {@link PdfStamper} not recognize the servlet output stream
     */
    @Override
    public PdfDocument generatePdf(Invoice invoice, HttpServletResponse servletResponse)
            throws IOException, DocumentException {

        Order order = orderService.findOrderById(invoice.getOrderId());


        PdfReader pdfReader = new PdfReader(PDF_TEMPLATE);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, servletResponse.getOutputStream());
        PdfContentByte content = pdfStamper.getOverContent(1);

        fntUbuntu = FontFactory.getFont(UBUNTU_FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
        baseFont = fntUbuntu.getBaseFont();

        content.beginText();

        addReference(invoice.getReference(), content);
        addOrderDate(order.getOrderDate(), content);

        User user = proxyService.findUserById(order.getCustomerId());

        // Check user data
        if(user.getFirstName() == null)
            throw new UserMalFormedException("User first name is null");
        if(user.getName() == null)
            throw new UserMalFormedException("User name is null");
        if(user.getTel() == null)
            throw new UserMalFormedException("User tel is null");
        if(user.getEmail() == null)
            throw new UserMalFormedException("User email is null");

        // Check user address data
        if(user.getAddress() == null)
            throw new UserMalFormedException("User address is null");
        if(user.getAddress().getStreetNumber() == null)
            throw new UserMalFormedException("The user's address have no street number");
        if(user.getAddress().getStreetName() == null)
            throw new UserMalFormedException("The user's address have no street name");
        if(user.getAddress().getCity().getName() == null)
            throw new UserMalFormedException("The user's address have no city name");
        if(user.getAddress().getCity().getZipCode() == null)
            throw new UserMalFormedException("The user's address have no zip code");

        addCustomer(user, content);

        List<OrderedProduct> productList = orderedProductService.findAllOrderedProductsByOrder(order);

        double totalAmount = addProducts(productList, content);

        addAmountDetails(totalAmount, productList.size(), content);

        addPaidImage(content);

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
                user.getFirstName() + " " + user.getName(), 50, 546, 0);

        content.setFontAndSize(baseFont, 9);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                user.getAddress().getStreetName(), 50, 533,0);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                "Tel : " + user.getTel(), 50, 520,0);

        content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                "Email : " + user.getEmail(), 50, 507,0);
    }

    private double addProducts(List<OrderedProduct> productList, PdfContentByte content)
            throws DocumentException {

        PdfPTable table = new PdfPTable(4);
        table.setTotalWidth(new float[]{252.1f, 80.9f, 100.1f, 88.5f});
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBorderWidth(0.1f);

        double totalAmount = 0;

        for(OrderedProduct orderedProduct : productList){

            log.info("OrderedProduct = " + orderedProduct);

            int quantity = orderedProduct.getQuantity();

            Product product = proxyService.findProductById(orderedProduct.getProductId());
            if(product == null) continue;

            log.info("Product = " + product);

            double price = Math.round(product.getPrice() * 100.0) / 100.0;
            double total = Math.round((price * quantity) * 100.0) / 100.0;
            totalAmount += total;

            table.addCell(getCell(product.getName(), Element.ALIGN_LEFT, 0));
            table.addCell(getCell(String.valueOf(quantity), Element.ALIGN_CENTER, 0));
            table.addCell(getCell(PRICE_FORMAT.format(price), Element.ALIGN_CENTER, 0));
            table.addCell(getCell(PRICE_FORMAT.format(total), Element.ALIGN_RIGHT, 10));
        }

        table.writeSelectedRows(0, -1, 45.8f, 441f, content);

        return totalAmount;
    }

    private void addAmountDetails(double amount, int nbProducts, PdfContentByte content)
            throws DocumentException {

        double tva = Math.round((amount * 0.055) * 100.0) / 100.0;

        PdfPTable tableTotal = new PdfPTable(2);
        tableTotal.setTotalWidth(new float[]{79, 88.5f});
        tableTotal.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableTotal.getDefaultCell().setBorderWidth(Rectangle.NO_BORDER);
        tableTotal.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

        tableTotal.addCell(getCell("Total HT", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell(PRICE_FORMAT.format(amount), Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("T.V.A. 5.5%", Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell(PRICE_FORMAT.format(tva), Element.ALIGN_RIGHT, 10));
        tableTotal.addCell(getCell("Total TTC", Element.ALIGN_RIGHT, 10));

        amount = Math.round((amount + tva) * 100.0) / 100.0;
        tableTotal.addCell(getCell(PRICE_FORMAT.format(amount), Element.ALIGN_RIGHT, 10));

        float posY = 424 - (nbProducts * CELL_HEIGHT);
        tableTotal.writeSelectedRows(0, -1, 400, posY, content);
    }

    private PdfPCell getCell(String value, int align, float padding){

        fntUbuntu.setSize(9);
        PdfPCell cell = new PdfPCell(new Phrase(value, fntUbuntu));
        cell.setHorizontalAlignment(align);
        cell.setPaddingRight(padding);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(CELL_HEIGHT);
        return cell;
    }

    private void addPaidImage(PdfContentByte content) throws IOException, DocumentException {

        String imgUrl = "https://files.lpdm.kybox.fr/b0023b0b-7c88-457f-b9f1-bq9c4cf45d5c/paid.png";
        Image image = Image.getInstance(new URL(imgUrl));
        image.setAbsolutePosition(400, 500);
        image.scaleToFit(100, 74);
        content.addImage(image);
    }
}
