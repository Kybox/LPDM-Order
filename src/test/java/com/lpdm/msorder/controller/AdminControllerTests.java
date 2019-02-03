package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.service.*;
import com.lpdm.msorder.utils.ObjToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.lpdm.msorder.utils.ValueType.EMAIL;
import static com.lpdm.msorder.utils.ValueType.NAME;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderedProductService orderedProductService;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private CouponService couponService;

    private int randomId;
    private Order order;
    private Payment payment;
    private List<Order> orderList;
    private List<OrderedProduct> orderedProductList;
    private Invoice invoice;
    private Delivery delivery;
    private Coupon coupon;
    private List<Coupon> couponList;

    @Before
    public void init(){

        order = new Order();
        order.setId(45);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setOrder(order);
        orderedProduct.setPrice(12.69);
        orderedProduct.setProduct(new Product(1));

        orderedProductList = new ArrayList<>();
        orderedProductList.add(orderedProduct);

        payment = new Payment("AmericanExpress");
        payment.setId(6);

        order.setOrderedProducts(orderedProductList);
        order.setPayment(payment);
        order.setCustomerId(2);
        order.setStatus(Status.CART);

        orderList = new ArrayList<>();
        orderList.add(order);

        randomId = (int) (Math.random()*123);

        String invoiceRef = "12345";
        invoice = new Invoice();
        invoice.setOrderId(order.getId());
        invoice.setReference(invoiceRef);
        invoice.setId(50);

        delivery = new Delivery();
        delivery.setId(randomId);
        delivery.setAmount(156.24);
        delivery.setMethod("A delivery method");

        coupon = new Coupon();
        coupon.setCode("A secret code");
        coupon.setActive(true);
        coupon.setAmount(942.14);
        coupon.setDescription("A description");
        coupon.setId(randomId);

        couponList = new ArrayList<>();
        couponList.add(coupon);
    }

    @Test
    public void addNewPayment() throws Exception {

        Mockito.when(paymentService.savePayment(Mockito.any(Payment.class)))
                .thenReturn(payment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/admin/payment/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(payment));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(payment)))
                .andDo(print());
    }

    @Test
    public void deletePayment() throws Exception {

        Mockito.when(paymentService.findPaymentById(Mockito.anyInt()))
                .thenReturn(payment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/admin/payment/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(payment));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    public void deleteOrder() throws Exception {

        Mockito.when(orderService.findOrderById(Mockito.anyInt()))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/admin/order/delete")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(order));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    public void findAllSortedByDate() throws Exception {

        Mockito.when(orderService.findAllOrdersPageable(Mockito.any(PageRequest.class)))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/date/asc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/date/desc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/date/bad");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void findAllByProductId() throws Exception {

        Mockito.when(orderedProductService.findAllOrderedProductsByProductId(Mockito.anyInt()))
                .thenReturn(orderedProductList);

        Mockito.when(orderService.findOrderById(Mockito.anyInt()))
                .thenReturn(order);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/product/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void findAllByPaymentId() throws Exception {

        Mockito.when(paymentService.findPaymentById(Mockito.anyInt())).thenReturn(payment);
        Mockito.when(orderService.findAllOrdersByPayment(Mockito.any(Payment.class)))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/payment/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void findAllByStatus() throws Exception {

        Mockito.when(orderService.findAllOrdersByStatusPageable(Mockito.any(Status.class), Mockito.any(PageRequest.class)))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/status/1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void findByInvoiceRef() throws Exception {

        String ref = "1223EHDe58";
        Mockito.when(invoiceService.findInvoiceByReference(ref))
                .thenReturn(invoice);

        Mockito.when(orderService.findOrderById(order.getId()))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/invoice/" + ref);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(order)))
                .andDo(print());
    }

    @Test
    public void findAllByEmail() throws Exception {

        String emailTest = "example@email.com";
        Mockito.when(orderService.findAllOrdersByCustomer(EMAIL, emailTest))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/customer/email/" + emailTest);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void findAllByName() throws Exception {

        String nameTest = "LeRoiDagobert";
        Mockito.when(orderService.findAllOrdersByCustomer(NAME, nameTest))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/customer/name/" + nameTest);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void findAllByDateBetween() throws Exception {

        Mockito.when(orderService.findAllOrdersBetweenTwoDates(Mockito.any(SearchDates.class)))
                .thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/orders/dates/between")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(new SearchDates()));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());
    }

    @Test
    public void addNewDeliveryMethod() throws Exception {

        Mockito.when(deliveryService.addNewDeliveryMethod(Mockito.any(Delivery.class)))
                .thenReturn(delivery);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/delivery/add")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(delivery));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(delivery)))
                .andDo(print());
    }

    @Test
    public void updateDelivery() throws Exception {

        Mockito.when(deliveryService.updateDeliveryMethod(Mockito.any(Delivery.class)))
                .thenReturn(delivery);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/admin/delivery/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(delivery));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(delivery)))
                .andDo(print());
    }

    @Test
    public void deleteDeliveryMethod() throws Exception {

        Mockito.when(deliveryService.deleteDeliveryMethod(Mockito.any(Delivery.class)))
                .thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/admin/delivery/delete")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(delivery));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    public void getCouponList() throws Exception {

        Mockito.when(couponService.findAllCoupons())
                .thenReturn(couponList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/coupon/all");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(couponList)))
                .andDo(print());
    }

    @Test
    public void addNewCoupon() throws Exception {

        Mockito.when(couponService.addNewCoupon(Mockito.any(Coupon.class)))
                .thenReturn(coupon);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/coupon/add")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(coupon));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(coupon)))
                .andDo(print());
    }

    @Test
    public void deleteCoupon() throws Exception {

        Mockito.when(couponService.deleteCoupon(Mockito.any(Coupon.class)))
                .thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/admin/coupon/delete")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(coupon));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    @Test
    public void updateCoupon() throws Exception {

        Mockito.when(couponService.updateCoupon(Mockito.any(Coupon.class)))
                .thenReturn(coupon);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/admin/coupon/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(coupon));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(coupon)))
                .andDo(print());
    }
}
