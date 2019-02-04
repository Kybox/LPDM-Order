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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyServiceTests {

    @Autowired
    private ProxyService proxyService;

    @MockBean
    private ProductProxy productProxy;

    @MockBean
    private AuthProxy authProxy;

    @MockBean
    private StoreProxy storeProxy;

    @MockBean
    private LocationProxy locationProxy;

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

        assertEquals(product, proxyService.findProductById(randomId));
    }

    @Test
    public void findAllProductCategories() {

        when(productProxy.findAllCategories())
                .thenReturn(categoryList);

        assertEquals(categoryList, proxyService.findAllProductCategories());
    }

    @Test
    public void findUserById() {

        when(authProxy.findById(anyInt())).thenReturn(user);

        assertEquals(user, proxyService.findUserById(randomId));
    }

    @Test
    public void findUserByLastName() {

        when(authProxy.findByLastName(anyString()))
                .thenReturn(user);

        assertEquals(user, proxyService.findUserByLastName("luc"));
    }

    @Test
    public void findUserByEmail() {

        when(authProxy.findByEmail(anyString()))
                .thenReturn(user);

        assertEquals(user, proxyService.findUserByEmail("demo@mail.com"));
    }

    @Test
    public void findStoreById() {

        when(storeProxy.findById(anyInt()))
                .thenReturn(store);

        assertEquals(store, proxyService.findStoreById(randomId));
    }

    @Test
    public void findAddressById() {

        when(locationProxy.findAddressById(anyInt()))
                .thenReturn(address);

        assertEquals(address, proxyService.findAddressById(randomId));
    }
}
