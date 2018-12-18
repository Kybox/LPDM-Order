package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderController orderController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void getOrderById() throws Exception {

        Order order = new Order();
        order.setId(1);
        order.setTotal(22.87);

        Mockito.when(orderController.getOrderById(1)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("total", is(22.87)));

        Mockito.verify(orderController, Mockito.times(1)).getOrderById(1);
        Mockito.verifyNoMoreInteractions(orderController);
    }

}
