package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.dao.OrderedProductRepository;
import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.model.OrderedProduct;
import com.lpdm.msorder.model.Product;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.proxy.ProductProxy;
import com.lpdm.msorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    private ProductProxy productProxy;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public Optional<Order> getById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderedProduct> getOrderedProductsByOrder(Order order) {
        return orderedProductRepository.findAllByOrder(order);
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productProxy.findById(id);
    }
}
