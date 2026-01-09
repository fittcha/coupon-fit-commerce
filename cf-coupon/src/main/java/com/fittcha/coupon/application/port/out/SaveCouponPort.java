package com.fittcha.coupon.application.port.out;

import com.fittcha.coupon.domain.Coupon;

public interface SaveCouponPort {

    Coupon save(Coupon coupon);
}
