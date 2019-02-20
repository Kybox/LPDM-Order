package com.lpdm.msorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lpdm.msorder"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .groupName("json")
                .enableUrlTemplating(true);
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "LPDM - MS-ORDER",
                "Microservice Order",
                "Version 1.0",
                "https://github.com/vyjorg/LPDM-Order/blob/master/LICENSE",
                new Contact("Yan K.", "https://lpdm.kybox.fr", "nslr@riseup.net"),
                "https://github.com/vyjorg/LPDM-Order/blob/master/LICENSE",
                "https://github.com/vyjorg/LPDM-Order/blob/master/LICENSE",
                Collections.emptyList());
    }
}
