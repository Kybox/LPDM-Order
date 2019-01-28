package com.lpdm.msorder.controller;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.order.Status;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.service.InvoiceService;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.ProxyService;
import com.lpdm.msorder.utils.ObjToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private InvoiceService invoiceService;

    @MockBean
    private ProxyService proxyService;

    @MockBean
    private FormatJson formatJson;

    private int randomId;
    private Order order;
    private Payment payment;
    private List<Order> orderList;
    private List<OrderedProduct> orderedProductList;

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
    }

    @Test
    public void addNewPayment() throws Exception {

        Mockito.when(orderService.savePayment(Mockito.any(Payment.class))).thenReturn(payment);

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

        Optional<Payment> optionalPayment = Optional.empty();
        Mockito.when(orderService.findPaymentById(Mockito.anyInt())).thenReturn(optionalPayment);

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

        Optional<Order> optionalOrder = Optional.empty();
        Mockito.when(orderService.findOrderById(Mockito.anyInt())).thenReturn(optionalOrder);

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

        PageImpl<Order> page = new PageImpl<>(orderList);
        Mockito.when(orderService.findAllOrdersPageable(Mockito.any(PageRequest.class))).thenReturn(page);

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

        Mockito.when(orderService.findAllOrderedProductsByProductId(Mockito.anyInt()))
                .thenReturn(orderedProductList);

        Optional<Order> optionalOrder = Optional.ofNullable(order);
        Mockito.when(orderService.findOrderById(Mockito.anyInt())).thenReturn(optionalOrder);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/product/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void findAllByPaymentId() throws Exception {

        Optional<Payment> optionalPayment = Optional.ofNullable(payment);
        Mockito.when(orderService.findPaymentById(Mockito.anyInt())).thenReturn(optionalPayment);
        Mockito.when(orderService.findAllOrdersByPayment(Mockito.any(Payment.class))).thenReturn(orderList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/all/payment/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
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
                .andDo(print());
    }
}
