package com.fittcha.coupon.application.port.out;

import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;

import java.util.List;
import java.util.Optional;

public interface LoadMemberCouponPort {

    Optional<MemberCoupon> findById(Long id);

    List<MemberCoupon> findByMemberId(Long memberId);

    List<MemberCoupon> findByMemberIdAndStatus(Long memberId, MemberCouponStatus status);

    boolean existsByMemberIdAndCouponId(Long memberId, Long couponId);
}
