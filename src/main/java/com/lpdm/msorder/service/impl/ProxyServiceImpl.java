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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class ProxyServiceImpl implements ProxyService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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
    public User findUserById(int id) {

        User user;

        try{ user = authProxy.findById(id); }

        catch (FeignException e){
            log.warn(e.getMessage());
            user = new User(id);
        }

        return user;
    }

    @Override
    public User findUserByLastName(String name) {
        return authProxy.findByLastName(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return authProxy.findByEmail(email);
    }

    @Override
    public Optional<Store> findStoreById(int id) {
        if(id == 0) return Optional.empty();
        return storeProxy.findById(id);
    }

    @Override
    public Address findAddressById(int id) {

        Address address;

        try { address = locationProxy.findAddressById(id); }

        catch (FeignException e){
            log.warn(e.getMessage());
            address = new Address(id);
        }

        return address;
    }
}
