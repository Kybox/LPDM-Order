package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(){
        super("No coupon code was found");
    }
}
