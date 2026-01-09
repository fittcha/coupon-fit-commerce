package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.MemberCoupon;

public interface IssueCouponUseCase {

    MemberCoupon issue(Long memberId, Long couponId);
}
