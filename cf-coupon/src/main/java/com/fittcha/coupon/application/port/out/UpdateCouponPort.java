package com.fittcha.coupon.application.port.out;

import com.fittcha.coupon.domain.Coupon;

public interface UpdateCouponPort {

    Coupon update(Coupon coupon);
}
