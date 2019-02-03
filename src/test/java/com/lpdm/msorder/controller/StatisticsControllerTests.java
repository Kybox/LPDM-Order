package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.order.OrderStats;
import com.lpdm.msorder.service.StatisticsService;
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

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTests {

    @Autowired MockMvc mockMvc;

    @MockBean private StatisticsService statisticsService;

    private int randomId;
    private OrderStats orderStats;

    @Before
    public void init(){

        randomId = (int) (Math.random()*1234);

        orderStats = new OrderStats();
        orderStats.setDataStats(new HashMap<>());
    }

    @Test
    public void getOrderStatsByYear() throws Exception {

        when(statisticsService.getOrderStatsByYear(anyInt()))
                .thenReturn(orderStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/stats/year/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(print());

        orderStats.getDataStats().put("key", "value");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderStats)))
                .andDo(print());

        verify(statisticsService, Mockito.times(2))
                .getOrderStatsByYear(anyInt());
    }

    @Test
    public void getOrderStatsByYearAndMonth() throws Exception {

        when(statisticsService.getOrderStatsByYearAndMonth(anyInt(), anyInt()))
                .thenReturn(orderStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orders/stats/year/"+ randomId + "/month/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(print());

        orderStats.getDataStats().put("key", "value");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderStats)))
                .andDo(print());

        verify(statisticsService, Mockito.times(2))
                .getOrderStatsByYearAndMonth(anyInt(), anyInt());
    }

    @Test
    public void getOrderedProductsByYear() throws Exception {

        when(statisticsService.getOrderedProductsStatsByYear(anyInt()))
                .thenReturn(orderStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orderedproducts/stats/year/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(print());

        orderStats.getDataStats().put("key", "value");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderStats)))
                .andDo(print());

        verify(statisticsService, Mockito.times(2))
                .getOrderedProductsStatsByYear(anyInt());
    }

    @Test
    public void getOrderedProductsByYearAndCategories() throws Exception {

        when(statisticsService.getOrderedProductsStatsByYearAndCategory(anyInt()))
                .thenReturn(orderStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/orderedproducts/stats/year/" + randomId + "/category");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(print());

        orderStats.getDataStats().put("key", "value");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(orderStats)))
                .andDo(print());

        verify(statisticsService, Mockito.times(2))
                .getOrderedProductsStatsByYearAndCategory(anyInt());
    }
}
