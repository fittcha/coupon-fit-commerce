package com.fittcha.coupon.application.port.out;

import com.fittcha.coupon.domain.MemberCoupon;

public interface SaveMemberCouponPort {

    MemberCoupon save(MemberCoupon memberCoupon);
}
