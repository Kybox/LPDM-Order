package com.lpdm.msorder.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(){
        super("No coupon code was found");
    }
}
