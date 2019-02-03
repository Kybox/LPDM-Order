package com.lpdm.msorder.service;

import com.lpdm.msorder.MsOrderApplication;
import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock private OrderRepository orderRepository;
    @Mock
    private FormatJson formatJson;

    int randomId;
    //private Order order;
    private List<Order> orderList;
    private Payment payment;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);

        randomId = (int) (Math.random()*123);
        //order = new Order(randomId);

        orderList = new ArrayList<>();
        orderList.add(new Order(randomId));


        payment = new Payment(randomId);
    }

    @Test
    public void saveOrder() {

        Order order = new Order(randomId);
        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        when(orderService.saveOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(order, orderService.saveOrder(order));
    }

    @Test
    public void findAllOrdersByCustomerId() {

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(orderList);

        when(orderService.findAllOrdersByCustomerId(anyInt()))
                .thenReturn(orderList);

        assertEquals(orderList, orderService.findAllOrdersByCustomerId(randomId));
    }
}
