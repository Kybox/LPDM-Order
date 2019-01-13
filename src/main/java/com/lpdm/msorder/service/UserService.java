package com.lpdm.msorder.service;

import com.lpdm.msorder.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(int id);
}
