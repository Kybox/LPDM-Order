package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.PaymentNotFoundException;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.service.impl.PaymentServiceImpl;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.RedirectUrls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentServiceTests {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock private OrderService orderService;
    @Mock private PaypalService paypalService;
    @Mock private PaymentRepository paymentRepository;

    private int randomId;
    private PaypalPayUrl paypalPayUrl;
    private Order order;
    private List<OrderedProduct> orderedProductList;

    private Payment payment;
    private List<Payment> paymentList;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        randomId = (int) (Math.random()*123);
        paypalPayUrl = new PaypalPayUrl();
        order = new Order(randomId);

        orderedProductList = new ArrayList<>();
        orderedProductList.add(new OrderedProduct());

        payment = new Payment(randomId);
        payment.setLabel("a payment method");
        paymentList = new ArrayList<>();
        paymentList.add(payment);
    }

    @Test
    public void findAllPayments() {

        when(paymentRepository.findAll())
                .thenReturn(paymentList);

        when(paymentService.findAllPayments())
                .thenReturn(paymentList);

        assertEquals(paymentList, paymentService.findAllPayments());
    }

    @Test
    public void savePayment() {

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        when(paymentService.savePayment(any(Payment.class)))
                .thenReturn(payment);

        assertEquals(payment, paymentService.savePayment(payment));
    }

    @Test
    public void checkIfPaymentExist() {

        assertFalse(paymentService.checkIfPaymentExist(randomId));
    }
}
