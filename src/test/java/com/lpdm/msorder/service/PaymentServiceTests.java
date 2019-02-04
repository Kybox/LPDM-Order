package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.service.impl.PaymentServiceImpl;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentServiceTests {

    @Autowired
    private PaymentServiceImpl paymentService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaypalService paypalService;

    @MockBean
    private PaymentRepository paymentRepository;

    private int randomId;
    private PaypalPayUrl paypalPayUrl;
    private Order order;
    private List<OrderedProduct> orderedProductList;

    private Payment payment;
    private List<Payment> paymentList;
    private List<Item> items;
    private PaypalToken paypalToken;
    private ItemList itemList;

    @Before
    public void init() {

        randomId = (int) (Math.random()*123);
        paypalPayUrl = new PaypalPayUrl();
        order = new Order(randomId);

        orderedProductList = new ArrayList<>();
        orderedProductList.add(new OrderedProduct());

        payment = new Payment(randomId);
        payment.setLabel("a payment method");
        paymentList = new ArrayList<>();
        paymentList.add(payment);

        items = new ArrayList<>();
        paypalToken = new PaypalToken();
        paypalToken.setAccess_token("test");

        itemList = new ItemList();
        itemList.setItems(items);
    }

    @Test
    public void findAllPayments() {

        when(paymentRepository.findAll())
                .thenReturn(paymentList);

        assertEquals(paymentList, paymentService.findAllPayments());
    }

    @Test
    public void savePayment() {

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);


        assertEquals(payment, paymentService.savePayment(payment));
    }

    @Test
    public void checkIfPaymentExist() {

        assertFalse(paymentService.checkIfPaymentExist(randomId));
    }
}
