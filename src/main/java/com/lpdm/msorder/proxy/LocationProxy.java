package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.location.Address;
import com.lpdm.msorder.model.product.Product;
import feign.FeignException;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.location.name}")
public interface LocationProxy {

    @RequestMapping(path = "${lpdm.location.name}/address/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Address findAddressById(@PathVariable(value = "id") int id) throws Exception;
}
