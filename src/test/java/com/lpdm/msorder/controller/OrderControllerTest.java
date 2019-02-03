package com.lpdm.msorder.controller;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.service.*;
import com.lpdm.msorder.utils.ObjToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private InvoiceService invoiceService;
    @MockBean private OrderService orderService;
    @MockBean private FormatJson formatJson;
    @MockBean private DeliveryService deliveryService;
    @MockBean private CouponService couponService;
    @MockBean private OrderedProductService orderedProductService;

    private int randomId;
    private Order order;
    private Payment payment;
    private List<Order> orderList;
    private List<OrderedProduct> orderedProductList;
    private Invoice invoice;
    private Delivery delivery;
    private List<Delivery> deliveryList;
    private Coupon coupon;

    @Before
    public void init(){

        randomId = (int) (Math.random()*123);

        order = new Order();
        order.setId(randomId);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setOrder(order);
        orderedProduct.setPrice(12.69);
        orderedProduct.setProduct(new Product(randomId));
        orderedProduct.setProductId(randomId);

        orderedProductList = new ArrayList<>();
        orderedProductList.add(orderedProduct);

        payment = new Payment("AmericanExpress");
        payment.setId(randomId);

        User user = new User(randomId);

        order.setOrderedProducts(orderedProductList);
        order.setPayment(payment);
        order.setCustomerId(randomId);
        order.setStatus(Status.CART);
        order.setCustomer(user);

        orderList = new ArrayList<>();
        orderList.add(order);

        invoice = new Invoice();
        invoice.setOrderId(randomId);
        invoice.setId(randomId);
        invoice.setReference("a ref");

        delivery = new Delivery();
        delivery.setId(randomId);
        delivery.setMethod("a method");
        delivery.setAmount(15.36);

        deliveryList = new ArrayList<>();
        deliveryList.add(delivery);

        coupon = new Coupon();
        coupon.setId(randomId);
        coupon.setCode("a code");
    }

    @Test
    public void getOrderById() throws Exception {

        when(orderService.findOrderById(anyInt()))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(order)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .findOrderById(anyInt());
    }

    @Test
    public void saveOrder() throws Exception {

        when(orderService.saveOrder(any(Order.class)))
                .thenReturn(order);

        when(orderedProductService.saveOrderedProduct(any(OrderedProduct.class)))
                .thenReturn(new OrderedProduct());

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/save")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(order));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(order)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .saveOrder(any(Order.class));

        verify(orderedProductService, Mockito.times(1))
                .saveOrderedProduct(any(OrderedProduct.class));

        verify(formatJson, Mockito.times(1))
                .formatOrder(any(Order.class));
    }

    @Test
    public void findAllByUserId() throws Exception {

        when(orderService.findAllOrdersByCustomerId(anyInt()))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/all/customer/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .findAllOrdersByCustomerId(anyInt());

        verify(formatJson, Mockito.times(1))
                .formatOrder(any(Order.class));
    }

    @Test
    public void findAllByUserAndStatus() throws Exception {

        when(orderService.findAllOrdersByCustomerIdAndStatus(anyInt(), any(Status.class)))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/all/customer/" + randomId + "/status/" + 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .findAllOrdersByCustomerIdAndStatus(anyInt(), any(Status.class));

        verify(formatJson, Mockito.times(1))
                .formatOrder(any(Order.class));
    }

    @Test
    public void findAllByUserOrderByDate() throws Exception {

        when(orderService.findAllOrdersByCustomerIdOrderByOrderDateAsc(anyInt(), any(PageRequest.class)))
                .thenReturn(orderList);

        when(orderService.findAllOrdersByCustomerIdOrderByOrderDateDesc(anyInt(), any(PageRequest.class)))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/all/customer/" + randomId + "/date/asc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        requestBuilder = MockMvcRequestBuilders
                .get("/orders/all/customer/" + randomId + "/date/desc");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .findAllOrdersByCustomerIdOrderByOrderDateAsc(anyInt(), any(PageRequest.class));

        verify(orderService, Mockito.times(1))
                .findAllOrdersByCustomerIdOrderByOrderDateDesc(anyInt(), any(PageRequest.class));

        verify(formatJson, Mockito.times(2))
                .formatOrder(any(Order.class));
    }

    @Test
    public void findByUserAndProduct() throws Exception {

        when(orderService.findAllOrdersByCustomerId(anyInt()))
                .thenReturn(orderList);

        when(orderedProductService.findAllOrderedProductsByOrder(any(Order.class)))
                .thenReturn(orderedProductList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/all/customer/" + randomId + "/product/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderList)))
                .andDo(print());

        verify(orderService, Mockito.times(1))
                .findAllOrdersByCustomerId(anyInt());

        verify(orderedProductService, Mockito.times(1))
                .findAllOrderedProductsByOrder(any(Order.class));

        verify(formatJson, Mockito.times(1))
                .formatOrder(any(Order.class));
    }

    @Test
    public void getAllDeliveryMethods() throws Exception {

        when(deliveryService.findAllDeliveryMethods())
                .thenReturn(deliveryList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/delivery/all");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(deliveryList)))
                .andDo(print());

        verify(deliveryService, Mockito.times(1))
                .findAllDeliveryMethods();
    }

    @Test
    public void checkCouponCode() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/coupon/check");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
