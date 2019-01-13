package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.model.User;
import com.lpdm.msorder.proxy.AuthProxy;
import com.lpdm.msorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AuthProxy authProxy;

    @Autowired
    public UserServiceImpl(AuthProxy authProxy) {
        this.authProxy = authProxy;
    }

    @Override
    public Optional<User> findUserById(int id) {
        return authProxy.findById(id);
    }
}
