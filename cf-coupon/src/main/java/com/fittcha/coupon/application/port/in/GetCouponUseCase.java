package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCouponUseCase {

    Coupon getById(Long id);

    Page<Coupon> getAll(Pageable pageable);
}
