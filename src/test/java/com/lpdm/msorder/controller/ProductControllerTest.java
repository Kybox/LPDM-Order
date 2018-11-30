package com.lpdm.msorder.controller;

import com.lpdm.msorder.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductController productController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void findProductById() throws Exception {

        Product product = new Product();
        int randomId = (int) (15 * Math.random());
        product.setId(randomId);
        product.setName("A product");

        Optional<Product> optionalProduct = Optional.of(product);

        Mockito.when(productController.findProductById(1)).thenReturn(optionalProduct);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("total", is(22.87)));

        Mockito.verify(productController, Mockito.times(1)).findProductById(1);
        Mockito.verifyNoMoreInteractions(productController);
    }

}
