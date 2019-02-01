package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.CouponNotFoundException;
import com.lpdm.msorder.model.order.Coupon;

import java.util.List;

public interface CouponService {

    /**
     * Find all {@link Coupon} codes in the database
     * @return A {@link Coupon} {@link List}
     * @throws CouponNotFoundException Thrown if no coupon was found
     */
    List<Coupon> findAllCoupons() throws CouponNotFoundException;

    /**
     * Adding a new {@link Coupon}
     * @param coupon The new {@link Coupon} object to add
     * @return The added {@link Coupon}
     */
    Coupon addNewCoupon(Coupon coupon);

    /**
     * Delete a {@link Coupon}
     * @param coupon The {@link Coupon} object to be deleted
     * @return True if the {@link Coupon} has been deleted, otherwise false
     */
    boolean deleteCoupon(Coupon coupon);

    /**
     * Check if the {@link Coupon} code is valid
     * @param code The {@link Coupon} code to check
     * @return The {@link Coupon} object if the code is valid
     * @throws CouponNotFoundException Thrown if the {@link Coupon} code is not valid
     */
    Coupon checkCouponCode(String code) throws CouponNotFoundException;
}
