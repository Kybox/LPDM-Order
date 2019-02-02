package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.DeliveryNotFoundException;
import com.lpdm.msorder.model.order.Delivery;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface DeliveryService {

    /**
     * Find all delivery methods
     * @return A {@link Delivery} list with all delivery methods
     */
    List<Delivery> findAllDeliveryMethods();

    /**
     * Find a delivry method by its ID
     * @param id The {@link Delivery} ID
     * @return A {@link Delivery} object if its ID was found
     * @throws DeliveryNotFoundException Thrown if the ID was not found
     */
    Delivery findDeliveryMethodById(int id) throws DeliveryNotFoundException;

    /**
     * Persist a new {@link Delivery} object
     * @param delivery The {@link Delivery} object to persist
     * @return the {@link Delivery} object that has been persisted
     */
    Delivery addNewDeliveryMethod(Delivery delivery);

    /**
     * Delete the {@link Delivery} object in the database
     * @param delivery The {@link Delivery} object to delete
     * @return True if the {@link Delivery} object was deleted, otherwise false
     */
    boolean deleteDeliveryMethod(Delivery delivery);

    /**
     * Update a {@link Delivery} object in the datebase
     * @param delivery The {@link Delivery} object to update
     * @return The {@link Delivery} object updated
     * @throws DeliveryNotFoundException Thrown if no {@link Delivery} object was found
     */
    Delivery updateDeliveryMethod(Delivery delivery) throws DeliveryNotFoundException;
}
