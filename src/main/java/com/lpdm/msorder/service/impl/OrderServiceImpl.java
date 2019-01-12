package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.dao.OrderRepository;
import com.lpdm.msorder.model.Order;
import com.lpdm.msorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Optional<Order> getById(int id) {
        return orderRepository.findById(id);
    }
}
