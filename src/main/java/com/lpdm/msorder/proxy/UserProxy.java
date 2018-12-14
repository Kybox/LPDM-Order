package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${microservice.userentity.name}",
        url = "${microservice.domain}"+":"+"${microservice.userentity.port}")
public interface UserProxy {

    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<User> findById(@PathVariable(value = "id") int id);
}