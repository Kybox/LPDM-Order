package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderStats;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsServiceTests {

    @Autowired
    private StatisticsService statisticsService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProxyService proxyService;

    private int randomInt;
    private long randomLong;
    private List<Category> categoryList;
    private List<Order> orderList;
    private Product product;

    @Before
    public void init() {

        randomInt = (int) (Math.random() * 12);
        randomLong = (long) (Math.random() * 12345);

        Category category = new Category(randomInt, "demo");
        categoryList = new ArrayList<>();
        categoryList.add(category);

        product = new Product(randomInt);
        product.setCategory(category);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setProductId(randomInt);

        Order order = new Order(randomInt);
        order.getOrderedProducts().add(orderedProduct);

        orderList = new ArrayList<>();
        orderList.add(order);
    }

    @Test
    public void getOrderStatsByYear() {

        when(orderService.countAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(randomLong);

        assertThat(statisticsService.getOrderedProductsStatsByYear(123),
                instanceOf(OrderStats.class));
    }

    @Test
    public void getOrderStatsByYearAndMonth() {

        when(orderService.countAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(randomLong);

        assertThat(statisticsService.getOrderStatsByYearAndMonth(randomInt, randomInt),
                instanceOf(OrderStats.class));
    }

    @Test
    public void getOrderedProductsStatsByYear() {

        when(orderService.findAllOrdersByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(orderList);

        assertThat(statisticsService.getOrderedProductsStatsByYear(randomInt),
                instanceOf(OrderStats.class));
    }

    @Test
    public void getOrderedProductsStatsByYearAndCategory() {

        when(proxyService.findAllProductCategories())
                .thenReturn(categoryList);

        when(orderService.findAllOrdersByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(orderList);

        when(proxyService.findProductById(anyInt()))
                .thenReturn(product);

        assertThat(statisticsService.getOrderedProductsStatsByYearAndCategory(randomInt),
                instanceOf(OrderStats.class));
    }
}
