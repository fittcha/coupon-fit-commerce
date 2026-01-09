package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.Coupon;

public interface CreateCouponUseCase {

    Coupon create(CreateCouponCommand command);
}
