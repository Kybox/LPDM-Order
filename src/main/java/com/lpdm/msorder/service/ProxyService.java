package com.lpdm.msorder.service;

import com.lpdm.msorder.model.Product;
import com.lpdm.msorder.model.Store;
import com.lpdm.msorder.model.User;
import feign.FeignException;

import java.util.Optional;

public interface ProxyService {

    /**
     * Call the product microservice to find a {@link Product} by its id
     * @param id The {@link Product} id
     * @return An {@link Optional<Product>} object
     */
    Optional<Product> findProductById(int id) throws FeignException;

    /**
     * Call the auth microservice to find a {@link User} by its id
     * @param id The {@link User} id
     * @return An {@link Optional<User>} object
     */
    Optional<User> findUserById(int id);
    Optional<User> findUserByLastName(String lastName);
    Optional<User> findUserByEmail(String email);

    /**
     * Call the store microservice to find a {@link Store} by its id
     * @param id The {@link Store} id
     * @return An {@link Optional<Store>} object
     */
    Optional<Store> findStoreById(int id);
}