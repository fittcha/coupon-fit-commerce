package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;

import java.util.List;

public interface GetMemberCouponUseCase {

    List<MemberCoupon> getByMemberId(Long memberId);

    List<MemberCoupon> getByMemberIdAndStatus(Long memberId, MemberCouponStatus status);
}
