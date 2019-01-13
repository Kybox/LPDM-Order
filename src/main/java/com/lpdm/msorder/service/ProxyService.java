package com.lpdm.msorder.service;

import com.lpdm.msorder.model.Product;
import com.lpdm.msorder.model.User;

import java.util.Optional;

public interface ProxyService {

    Optional<Product> findProductById(int id);

    Optional<User> findUserById(int id);
}
