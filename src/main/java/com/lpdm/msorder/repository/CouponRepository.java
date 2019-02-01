package com.lpdm.msorder.repository;

import com.lpdm.msorder.model.order.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * Find all activated {@link Coupon}
     * @return The {@link List} of activated {@link Coupon}
     */
    List<Coupon> findAllByActiveIsTrue();
}
