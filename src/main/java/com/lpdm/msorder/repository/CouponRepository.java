package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * Find all activated {@link Coupon}
     * @return The {@link List} of activated {@link Coupon}
     */
    List<Coupon> findAllByActiveIsTrue();
}
