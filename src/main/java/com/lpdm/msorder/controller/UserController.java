package com.lpdm.msorder.controller;

import com.lpdm.msorder.model.User;
import com.lpdm.msorder.proxy.UserProxy;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LogManager.getLogger(this.getClass());

    private final UserProxy userProxy;

    @Autowired
    public UserController(UserProxy userProxy) {
        this.userProxy = userProxy;
    }

    @RequestMapping("/{id}")
    public Optional<User> findUserById(@PathVariable int id){

        Optional<User> optionalUser = Optional.empty();

        try { optionalUser = userProxy.findById(id); }
        catch (FeignException e) { log.error(e.getMessage()); }

        return optionalUser;
    }
}
