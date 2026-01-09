package com.fittcha.coupon.domain;

/**
 * 회원 보유 쿠폰 상태
 * - AVAILABLE: 사용 가능
 * - USED: 사용 완료
 * - EXPIRED: 기간 만료
 */
public enum MemberCouponStatus {
    AVAILABLE,
    USED,
    EXPIRED
}
