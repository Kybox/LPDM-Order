package com.lpdm.msorder.service;

import com.lpdm.msorder.model.Order;

import java.util.Optional;

public interface OrderService {

    Optional<Order> getById(int id);
}
