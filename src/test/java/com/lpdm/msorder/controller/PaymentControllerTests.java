package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.service.PaymentService;
import com.lpdm.msorder.service.PaypalService;
import com.lpdm.msorder.utils.ObjToJson;
import com.paypal.api.payments.RedirectUrls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTests {

    @Autowired MockMvc mockMvc;

    @MockBean private PaymentService paymentService;
    @MockBean private PaypalService paypalService;

    private int randomId;
    private PaypalPayUrl paypalPayUrl;
    private List<Payment> paymentList;

    @Before
    public void init(){

        randomId = (int) (Math.random()*123);

        paypalPayUrl = new PaypalPayUrl();
        paypalPayUrl.setRedirectUrl("a url");

        paymentList = new ArrayList<>();
        paymentList.add(new Payment());
    }

    @Test
    public void payOrder() throws Exception {

        when(paymentService.paypalPaymentProcess(anyInt(), any(RedirectUrls.class), anyString(), anyString()))
                .thenReturn(paypalPayUrl);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/" + randomId + "/pay")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(new RedirectUrls()));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(paypalPayUrl)))
                .andDo(print());

        verify(paymentService, Mockito.times(1))
                .paypalPaymentProcess(anyInt(), any(RedirectUrls.class), anyString(), anyString());
    }

    @Test
    public void returnPayment() throws Exception {

        when(paypalService.getTransactionDetails(any(PaypalReturn.class), anyString(), anyString()))
                .thenReturn("ok");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/transaction/details")
                .param("paymentId", String.valueOf(randomId))
                .param("token", "a token")
                .param("PayerID", String.valueOf(randomId));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("ok"))
                .andDo(print());

        verify(paypalService, Mockito.times(1))
                .getTransactionDetails(any(PaypalReturn.class), anyString(), anyString());
    }

    @Test
    public void getPaymentList() throws Exception {

        when(paymentService.findAllPayments())
                .thenReturn(paymentList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/orders/payments");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(paymentList)))
                .andDo(print());

        verify(paymentService, Mockito.times(1))
                .findAllPayments();
    }
}
