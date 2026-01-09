package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.Coupon;

public interface UpdateCouponUseCase {

    Coupon update(Long id, UpdateCouponCommand command);
}
