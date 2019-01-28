package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.product.Category;
import com.lpdm.msorder.model.product.Product;
import feign.FeignException;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.product.name}")
public interface ProductProxy {

    @RequestMapping(path = "${lpdm.product.name}/products/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Product findById(@PathVariable(value = "id") int id) throws FeignException;

    @RequestMapping(path = "${lpdm.product.name}/categories",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Category> findAllCategories() throws FeignException;
}