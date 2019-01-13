package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.model.Product;
import com.lpdm.msorder.model.User;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.proxy.ProductProxy;
import com.lpdm.msorder.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProxyServiceImpl implements ProxyService {

    private final ProductProxy productProxy;
    private final AuthProxy authProxy;

    @Autowired
    public ProxyServiceImpl(ProductProxy productProxy, AuthProxy authProxy) {
        this.productProxy = productProxy;
        this.authProxy = authProxy;
    }

    @Override
    public Optional<Product> findProductById(int id) {
        return productProxy.findById(id);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return authProxy.findById(id);
    }
}
