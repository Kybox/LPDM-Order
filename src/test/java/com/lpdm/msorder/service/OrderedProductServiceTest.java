package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.repository.OrderedProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderedProductServiceTest {

    @MockBean
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    private OrderedProductService orderedProductService;

    private int randomId;
    private Order order;
    private OrderedProduct orderedProduct;
    private List<OrderedProduct> orderedProductList;

    @Before
    public void init(){

        randomId = (int) (Math.random() * 123);

        order = new Order(randomId);
        orderedProduct = new OrderedProduct();

        orderedProductList = new ArrayList<>();
        orderedProductList.add(orderedProduct);
    }

    @Test
    public void saveOrderedProduct() {

        when(orderedProductRepository.save(any(OrderedProduct.class)))
                .thenReturn(orderedProduct);

        assertEquals(orderedProduct, orderedProductService
                .saveOrderedProduct(orderedProduct));
    }

    @Test
    public void findAllOrderedProductsByProductId() {

        when(orderedProductRepository.findAllByProductId(anyInt()))
                .thenReturn(orderedProductList);

        assertEquals(orderedProductList, orderedProductService
                .findAllOrderedProductsByProductId(randomId));
    }

    @Test
    public void findAllOrderedProductsByOrder() {

        when(orderedProductRepository.findAllByOrder(any(Order.class)))
                .thenReturn(orderedProductList);

        assertEquals(orderedProductList, orderedProductService
                .findAllOrderedProductsByOrder(new Order()));
    }
}
