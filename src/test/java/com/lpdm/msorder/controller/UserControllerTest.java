package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.User;
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
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserController userController;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void findUserById() throws Exception {

        User user = new User();
        user.setId(1);
        user.setFirstName("myFirstName");
        user.setLastName("myLastName");
        Optional<User> optionalUser = Optional.of(user);

        Mockito.when(userController.findUserById(1)).thenReturn(optionalUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("myFirstName")))
                .andExpect(jsonPath("lastName", is("myLastName")));

        Mockito.verify(userController, Mockito.times(1)).findUserById(1);
        Mockito.verifyNoMoreInteractions(userController);
    }

}
