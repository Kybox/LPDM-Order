package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
