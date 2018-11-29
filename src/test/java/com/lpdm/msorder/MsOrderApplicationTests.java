package com.lpdm.msorder;

import static org.assertj.core.api.Assertions.assertThat;

import com.lpdm.msorder.controller.OrderController;
import com.lpdm.msorder.controller.StoreController;
import com.lpdm.msorder.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsOrderApplicationTests {

	@Autowired OrderController orderController;
	@Autowired StoreController storeController;
	@Autowired UserController userController;

	@Test
	public void contextLoads() {

		assertThat(orderController).isNotNull();
		assertThat(storeController).isNotNull();
		assertThat(userController).isNotNull();
	}

}
