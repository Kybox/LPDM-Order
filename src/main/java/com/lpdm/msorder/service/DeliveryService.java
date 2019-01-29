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
     * @return A {@link List<Delivery>} with all delivery methods
     */
    List<Delivery> findAllDeliveryMethods();

    /**
     * Find a delivry method by its ID
     * @param id The {@link Delivery} ID
     * @return A {@link Delivery} object if its ID was found
     * @throws DeliveryNotFoundException Thrown if the ID was not found
     */
    Delivery findDeliveryMethodById(int id) throws DeliveryNotFoundException;
}
