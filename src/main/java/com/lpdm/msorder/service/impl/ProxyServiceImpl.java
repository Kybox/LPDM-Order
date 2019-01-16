package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.model.Product;
import com.lpdm.msorder.model.Store;
import com.lpdm.msorder.model.User;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.proxy.ProductProxy;
import com.lpdm.msorder.proxy.StoreProxy;
import com.lpdm.msorder.service.ProxyService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProxyServiceImpl implements ProxyService {

    private final ProductProxy productProxy;
    private final AuthProxy authProxy;
    private final StoreProxy storeProxy;

    @Autowired
    public ProxyServiceImpl(ProductProxy productProxy, AuthProxy authProxy, StoreProxy storeProxy) {
        this.productProxy = productProxy;
        this.authProxy = authProxy;
        this.storeProxy = storeProxy;
    }

    @Override
    public Product findProductById(int id) throws FeignException {
        return productProxy.findById(id);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return authProxy.findById(id);
    }

    @Override
    public Optional<User> findUserByLastName(String lastName) {
        return authProxy.findByLastName(lastName);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return authProxy.findByEmail(email);
    }

    @Override
    public Optional<Store> findStoreById(int id) {
        return storeProxy.findById(id);
    }
}
