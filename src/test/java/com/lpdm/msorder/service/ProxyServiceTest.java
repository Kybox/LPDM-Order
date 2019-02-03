package com.lpdm.msorder.service;

import com.lpdm.msorder.model.location.Address;
import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import com.lpdm.msorder.model.store.Store;
import com.lpdm.msorder.model.user.User;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.proxy.LocationProxy;
import com.lpdm.msorder.proxy.ProductProxy;
import com.lpdm.msorder.proxy.StoreProxy;
import com.lpdm.msorder.service.impl.ProxyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyServiceTest {

    @InjectMocks
    private ProxyServiceImpl proxyService;

    @Mock private ProductProxy productProxy;
    @Mock private AuthProxy authProxy;
    @Mock private StoreProxy storeProxy;
    @Mock private LocationProxy locationProxy;

    private int randomId;
    private Product product;
    private List<Category> categoryList;
    private User user;
    private Store store;
    private Address address;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);

        randomId = (int) (Math.random()*123);

        product = new Product();
        product.setId(randomId);

        categoryList = new ArrayList<>();
        categoryList.add(new Category());

        user = new User(randomId);
        store = new Store(randomId);
        address = new Address(randomId);
    }

    @Test
    public void findProductById() {

        when(productProxy.findById(anyInt()))
                .thenReturn(product);

        when(proxyService.findProductById(anyInt()))
                .thenReturn(product);

        assertEquals(product, proxyService.findProductById(randomId));
    }

    @Test
    public void findAllProductCategories() {

        when(productProxy.findAllCategories())
                .thenReturn(categoryList);

        when(proxyService.findAllProductCategories())
                .thenReturn(categoryList);

        assertEquals(categoryList, proxyService.findAllProductCategories());
    }

    @Test
    public void findUserById() {

        when(authProxy.findById(anyInt())).thenReturn(user);

        when(proxyService.findUserById(anyInt()))
                .thenReturn(user);

        assertEquals(user, proxyService.findUserById(randomId));
    }

    @Test
    public void findUserByLastName() {

        when(authProxy.findByLastName(anyString()))
                .thenReturn(user);

        when(proxyService.findUserByLastName(anyString()))
                .thenReturn(user);

        assertEquals(user, proxyService.findUserByLastName("luc"));
    }

    @Test
    public void findUserByEmail() {

        when(authProxy.findByEmail(anyString()))
                .thenReturn(user);

        when(proxyService.findUserByEmail(anyString()))
                .thenReturn(user);

        assertEquals(user, proxyService.findUserByEmail("demo@mail.com"));
    }

    @Test
    public void findStoreById() {

        when(storeProxy.findById(anyInt()))
                .thenReturn(store);

        when(proxyService.findStoreById(anyInt()))
                .thenReturn(store);

        assertEquals(store, proxyService.findStoreById(randomId));
    }

    @Test
    public void findAddressById() {

        when(locationProxy.findAddressById(anyInt()))
                .thenReturn(address);

        when(proxyService.findAddressById(anyInt()))
                .thenReturn(address);

        assertEquals(address, proxyService.findAddressById(randomId));
    }
}
