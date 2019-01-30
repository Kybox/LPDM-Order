package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
