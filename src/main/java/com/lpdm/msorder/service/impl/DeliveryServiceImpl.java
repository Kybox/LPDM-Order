package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.DeliveryNotFoundException;
import com.lpdm.msorder.model.order.Delivery;
import com.lpdm.msorder.repository.DeliveryRepository;
import com.lpdm.msorder.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {

        this.deliveryRepository = deliveryRepository;
    }

    /**
     * Find all delivery methods
     * @return A {@link List<Delivery>} with all delivery methods
     */
    @Override
    public List<Delivery> findAllDeliveryMethods() {

        return deliveryRepository.findAll();
    }

    /**
     * Find a delivry method by its ID
     * @param id The {@link Delivery} ID
     * @return A {@link Delivery} object if its ID was found
     * @throws DeliveryNotFoundException Thrown if the ID was not found
     */
    @Override
    public Delivery findDeliveryMethodById(int id) throws DeliveryNotFoundException {

        return deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
    }

    /**
     * Persist a new {@link Delivery} object
     * @param delivery The {@link Delivery} object to persist
     * @return the {@link Delivery} object that has been persisted
     */
    @Override
    public Delivery addNewDeliveryMethod(Delivery delivery) {

        return deliveryRepository.save(delivery);
    }

    /**
     * Delete the {@link Delivery} object in the database
     * @param delivery The {@link Delivery} object to delete
     * @return True if the {@link Delivery} object was deleted, otherwise false
     */
    @Override
    public boolean deleteDeliveryMethod(Delivery delivery) {

        deliveryRepository.delete(delivery);
        return !deliveryRepository.findById(delivery.getId()).isPresent();
    }

    /**
     * Update a {@link Delivery} object in the datebase
     * @param delivery The {@link Delivery} object to update
     * @return The {@link Delivery} object updated
     * @throws DeliveryNotFoundException Thrown if no {@link Delivery} object was found
     */
    @Override
    public Delivery updateDeliveryMethod(Delivery delivery) throws DeliveryNotFoundException {

        Optional<Delivery> optDelivery = deliveryRepository.findById(delivery.getId());
        if(!optDelivery.isPresent()) throw new DeliveryNotFoundException();

        return deliveryRepository.save(delivery);
    }
}
