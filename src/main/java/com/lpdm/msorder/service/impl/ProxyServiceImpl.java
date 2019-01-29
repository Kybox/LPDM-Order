package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.model.location.Address;
import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.store.Store;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.proxy.LocationProxy;
import com.lpdm.msorder.proxy.ProductProxy;
import com.lpdm.msorder.proxy.StoreProxy;
import com.lpdm.msorder.service.ProxyService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProxyServiceImpl implements ProxyService {

    private final LocationProxy locationProxy;
    private final ProductProxy productProxy;
    private final StoreProxy storeProxy;
    private final AuthProxy authProxy;

    @Autowired
    public ProxyServiceImpl(LocationProxy locationProxy,
                            ProductProxy productProxy,
                            StoreProxy storeProxy,
                            AuthProxy authProxy) {

        this.locationProxy = locationProxy;
        this.productProxy = productProxy;
        this.storeProxy = storeProxy;
        this.authProxy = authProxy;
    }

    @Override
    public Product findProductById(int id) throws FeignException {
        return productProxy.findById(id);
    }

    @Override
    public List<Category> findAllProductCategories() throws FeignException {
        return productProxy.findAllCategories();
    }

    @Override
    public Optional<User> findUserById(int id) {
        if(id == 0) return Optional.empty();
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
        if(id == 0) return Optional.empty();
        return storeProxy.findById(id);
    }

    @Override
    public Address findAddressById(int id) throws Exception {
        return locationProxy.findAddressById(id);
    }
}
