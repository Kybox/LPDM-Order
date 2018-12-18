package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.Store;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class StoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StoreController storeController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
    }

    @Test
    public void findStoreById() throws Exception {
        Store store = new Store();
        store.setId(1);
        store.setName("myStore");
        Optional<Store> optionalStore = Optional.of(store);

        Mockito.when(storeController.findStoreById(1)).thenReturn(optionalStore);

        mockMvc.perform(get("/stores/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("myStore")));

        Mockito.verify(storeController, Mockito.times(1)).findStoreById(1);
        Mockito.verifyNoMoreInteractions(storeController);
    }

}
