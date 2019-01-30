package com.lpdm.msorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MsOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrderApplication.class, args);
	}
}
