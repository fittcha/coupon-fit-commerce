package com.fittcha.coupon.application.port.out;

import com.fittcha.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadCouponPort {

    Optional<Coupon> findById(Long id);

    Page<Coupon> findAll(Pageable pageable);
}
