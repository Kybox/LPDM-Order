package com.lpdm.msorder.proxy;

import com.lpdm.msorder.model.user.User;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.auth.name}")
public interface AuthProxy {

    @RequestMapping(path = "${lpdm.auth.name}/users/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<User> findById(@PathVariable(value = "id") int id);

    @RequestMapping(path = "${lpdm.auth.name}/users/name/{name}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<User> findByLastName(@PathVariable(value = "name") String name);

    @RequestMapping(path = "${lpdm.auth.name}/users/email/{email}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<User> findByEmail(@PathVariable(value = "email") String name);
}
