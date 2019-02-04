package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.CouponNotFoundException;
import com.lpdm.msorder.model.order.Coupon;
import com.lpdm.msorder.repository.CouponRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CouponServiceTests {

    @MockBean
    private CouponRepository couponRepository;

    @Autowired
    CouponService couponService;

    private Coupon coupon;
    private List<Coupon> couponList;

    @Before
    public void init() {

        int randomId = (int) (Math.random() * 123);

        coupon = new Coupon(randomId);
        coupon.setCode("code");

        couponList = new ArrayList<>();
        couponList.add(coupon);
    }

    @Test(expected = CouponNotFoundException.class)
    public void findAllCouponsException() {

        List<Coupon> couponList = new ArrayList<>();

        when(couponRepository.findAll())
                .thenReturn(couponList);

        couponService.findAllCoupons();
    }

    @Test
    public void findAllCoupons() {

        when(couponRepository.findAll())
                .thenReturn(couponList);

        assertEquals(couponList, couponService.findAllCoupons());
    }

    @Test
    public void addNewCoupon() {

        Coupon coupon = new Coupon();
        coupon.setCode("a code");

        when(couponRepository.save(any(Coupon.class)))
                .thenReturn(coupon);

        assertEquals(coupon, couponService.addNewCoupon(coupon));
    }

    @Test(expected = CouponNotFoundException.class)
    public void deleteCouponException() {

        when(couponRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        couponService.deleteCoupon(coupon);
    }

    @Test
    public void deleteCoupon() {

        when(couponRepository.findById(anyInt()))
                .thenReturn(Optional.of(coupon));

        assertFalse(couponService.deleteCoupon(coupon));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkCouponCode() {

        when(couponRepository.findAllByActiveIsTrue())
                .thenReturn(couponList);

        couponService.checkCouponCode("code");
    }

    @Test(expected = CouponNotFoundException.class)
    public void updateCouponException() {

        when(couponRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        couponService.updateCoupon(coupon);
    }

    @Test
    public void updateCoupon() {

        when(couponRepository.findById(anyInt()))
                .thenReturn(Optional.of(coupon));

        when(couponRepository.save(any(Coupon.class)))
                .thenReturn(coupon);

        assertEquals(coupon, couponService.updateCoupon(coupon));
    }
}
