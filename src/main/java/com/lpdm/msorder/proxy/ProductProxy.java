package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${lpdm.product.name}", url = "${lpdm.product.uri")
public interface ProductProxy {

    @RequestMapping(path = "/products/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<Product> findById(@PathVariable(value = "id") int id);

}