package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${lpdm.store.name}", url = "${lpdm.store.uri}")
public interface StoreProxy {

    @RequestMapping(value = "/stores/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<Store> findById(@PathVariable(value = "id") int id);
}