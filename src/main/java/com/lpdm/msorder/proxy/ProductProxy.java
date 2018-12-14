package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${microservice.product.name}",
        url = "${microservice.domain}"+":"+"${microservice.product.port}")
public interface ProductProxy {

    @RequestMapping(path = "/products/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<Product> findById(@PathVariable(value = "id") int id);

}