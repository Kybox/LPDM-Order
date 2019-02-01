package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.CouponNotFoundException;
import com.lpdm.msorder.model.order.Coupon;
import com.lpdm.msorder.repository.CouponRepository;
import com.lpdm.msorder.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * Find all {@link Coupon} codes in the database
     * @return A {@link Coupon} {@link List}
     * @throws CouponNotFoundException Thrown if no coupon was found
     */
    @Override
    public List<Coupon> findAllCoupons() throws CouponNotFoundException {

        List<Coupon> couponList = couponRepository.findAll();
        if(couponList.isEmpty()) throw new CouponNotFoundException();
        else return couponList;
    }

    /**
     * Adding a new {@link Coupon}
     * @param coupon The new {@link Coupon} object to add
     * @return The added {@link Coupon}
     */
    @Override
    public Coupon addNewCoupon(Coupon coupon) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        coupon.setCode(passwordEncoder.encode(coupon.getCode()));
        return couponRepository.save(coupon);
    }

    /**
     * Delete a {@link Coupon}
     * @param coupon The {@link Coupon} object to be deleted
     * @return True if the {@link Coupon} has been deleted, otherwise false
     * @throws CouponNotFoundException Thrown if no {@link Coupon} was found in the database
     */
    @Override
    public boolean deleteCoupon(Coupon coupon) throws CouponNotFoundException {

        log.info("Delete coupon : " + coupon);

        Optional<Coupon> optCoupon = couponRepository.findById(coupon.getId());
        if(!optCoupon.isPresent()) throw new CouponNotFoundException();

        coupon.setCode(optCoupon.get().getCode());
        couponRepository.delete(coupon);

        optCoupon = couponRepository.findById(coupon.getId());

        return !optCoupon.isPresent();
    }

    /**
     * Check if the {@link Coupon} code is valid
     * @param code The {@link Coupon} code to check
     * @return The {@link Coupon} object if the code is valid
     * @throws CouponNotFoundException Thrown if the {@link Coupon} code is not valid
     */
    @Override
    public Coupon checkCouponCode(String code) throws CouponNotFoundException {

        List<Coupon> couponList = couponRepository.findAllByActiveIsTrue();

        for(Coupon coupon : couponList){
            if(BCrypt.checkpw(code, coupon.getCode()))
                return coupon;
        }

        throw new CouponNotFoundException();
    }

    /**
     * Update an existing {@link Coupon} object
     * @param coupon The {@link Coupon} object to update
     * @return The {@link Coupon} object updated
     * @throws CouponNotFoundException Thrown if no {@link Coupon} was found in the database
     */
    @Override
    public Coupon updateCoupon(Coupon coupon) throws CouponNotFoundException{

        Optional<Coupon> optCoupon = couponRepository.findById(coupon.getId());
        if(!optCoupon.isPresent()) throw new CouponNotFoundException();

        coupon.setCode(optCoupon.get().getCode());

        return couponRepository.save(coupon);
    }
}
