package com.lpdm.msorder.service;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.*;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.repository.OrderRepository;
import com.lpdm.msorder.repository.OrderedProductRepository;
import com.lpdm.msorder.service.impl.OrderServiceImpl;
import com.lpdm.msorder.utils.OrderUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lpdm.msorder.utils.ValueType.EMAIL;
import static com.lpdm.msorder.utils.ValueType.NAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTests {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private FormatJson formatJson;

    @MockBean
    private ProxyService proxyService;


    int randomId;
    private Order order;
    private List<Order> orderList;
    private Payment payment;
    private Product product;

    @Before
    public void init(){

        randomId = (int) (Math.random()*123) + 100;

        product = new Product(randomId);
        product.setPrice(randomId);
        product.setTax(randomId);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setProduct(product);

        List<OrderedProduct> productList = new ArrayList<>();
        productList.add(orderedProduct);

        order = new Order(randomId);
        order.setStatus(Status.CART);
        order.setCustomer(new User(randomId));
        order.setOrderedProducts(productList);
        order.setTaxAmount(randomId);
        order.setTotal(randomId);

        orderList = new ArrayList<>();
        orderList.add(new Order(randomId));

        payment = new Payment(randomId);
    }

    @Test
    public void saveOrder() {

        when(proxyService.findProductById(anyInt()))
                .thenReturn(product);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        //assertEquals(order, orderService.saveOrder(order));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findOrderByIdException() {

        when(orderRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        orderService.findOrderById(randomId);
    }

    @Test
    public void findOrderById() {

        when(orderRepository.findById(anyInt()))
                .thenReturn(Optional.of(order));

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(order, orderService.findOrderById(randomId));
    }

    @Test
    public void checkIfOrderExist() {

        when(orderRepository.findById(anyInt()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(order));

        assertFalse(orderService.checkIfOrderExist(randomId));

        assertTrue(orderService.checkIfOrderExist(randomId));
    }

    @Test
    public void findAllOrdersByCustomerId() {

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(orderList);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomerId(randomId));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersByPaymentException() {

        when(orderRepository.findAllByPayment(any(Payment.class)))
                .thenReturn(new ArrayList<>());

        orderService.findAllOrdersByPayment(new Payment());
    }

    @Test
    public void findAllOrdersByPayment() {

        when(orderRepository.findAllByPayment(any(Payment.class)))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(orderList, orderService
                .findAllOrdersByPayment(new Payment()));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersPageableException() {

        when(orderRepository.findAll(any(PageRequest.class)))
                .thenReturn(Page.empty());

        orderService.findAllOrdersPageable(PageRequest.of(randomId, randomId));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersByCustomerEmailException() {

        when(proxyService.findUserByEmail(anyString()))
                .thenReturn(new User(randomId));

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(new ArrayList<>());

        orderService.findAllOrdersByCustomer(EMAIL, "demo@email.com");
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersByCustomerNameException() {

        when(proxyService.findUserByLastName(anyString()))
                .thenReturn(new User(randomId));

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(new ArrayList<>());

        orderService.findAllOrdersByCustomer(NAME, "demoName");
    }

    @Test
    public void findAllOrdersByCustomerEmail() {

        when(proxyService.findUserByEmail(anyString()))
                .thenReturn(new User(randomId));

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomer(EMAIL, "demo@email.com"));
    }

    @Test
    public void findAllOrdersByCustomerName() {

        when(proxyService.findUserByLastName(anyString()))
                .thenReturn(new User(randomId));

        when(orderRepository.findAllByCustomerId(anyInt()))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomer(NAME, "demoName"));
    }

    @Test
    public void findAllOrdersByCustomerIdAndStatus() {

        when(orderRepository.findAllByCustomerIdAndStatus(anyInt(), any(Status.class)))
                .thenReturn(orderList);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomerIdAndStatus(randomId, Status.PAID));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersBetweenTwoDatesException() {

        SearchDates searchDates = new SearchDates();
        searchDates.setDate1(LocalDate.now());
        searchDates.setDate2(LocalDate.now());

        when(orderRepository.findAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        orderService.findAllOrdersBetweenTwoDates(searchDates);
    }

    @Test
    public void findAllOrdersBetweenTwo() {

        SearchDates searchDates = new SearchDates();
        searchDates.setDate1(LocalDate.now());
        searchDates.setDate2(LocalDate.now());

        when(orderRepository.findAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(orderList, orderService.findAllOrdersBetweenTwoDates(searchDates));
    }

    @Test(expected = OrderNotFoundException.class)
    public void findAllOrdersByStatusPageableException() {

        when(orderRepository.findAllByStatus(any(Status.class), any(Pageable.class)))
                .thenReturn(new ArrayList<>());

        orderService.findAllOrdersByStatusPageable(Status.PAID, PageRequest.of(randomId, randomId));
    }

    @Test
    public void findAllOrdersByStatusPageable() {

        when(orderRepository.findAllByStatus(any(Status.class), any(Pageable.class)))
                .thenReturn(orderList);

        when(formatJson.formatOrder(any(Order.class)))
                .thenReturn(order);

        assertEquals(orderList, orderService
                .findAllOrdersByStatusPageable(Status.PAID, PageRequest.of(randomId, randomId)));
    }

    @Test
    public void findAllOrdersByCustomerIdOrderByOrderDateAsc() {

        when(orderRepository.findAllByCustomerIdOrderByOrderDateAsc(anyInt(), any(Pageable.class)))
                .thenReturn(orderList);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomerIdOrderByOrderDateAsc(randomId, PageRequest.of(randomId, randomId)));
    }

    @Test
    public void findAllOrdersByCustomerIdOrderByOrderDateDesc() {

        when(orderRepository.findAllByCustomerIdOrderByOrderDateDesc(anyInt(), any(Pageable.class)))
                .thenReturn(orderList);

        assertEquals(orderList, orderService
                .findAllOrdersByCustomerIdOrderByOrderDateDesc(randomId, PageRequest.of(randomId, randomId)));
    }

    @Test
    public void countAllByOrderDateBetween() {

        long total = 5000L;

        when(orderRepository.countAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(total);

        assertEquals(total, orderService
                .countAllByOrderDateBetween(LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void findAllOrdersByDateBetween() {

        when(orderRepository.findAllByOrderDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(orderList);

        assertEquals(orderList, orderService
                .findAllOrdersByDateBetween(LocalDateTime.now(), LocalDateTime.now()));
    }
}
