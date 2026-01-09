package com.fittcha.coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private Long id;
    private Long memberId;
    private Long couponId;
    private MemberCouponStatus status;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime usedAt;

    public static MemberCoupon issue(Long memberId, Coupon coupon) {
        validateMemberId(memberId);
        validateCoupon(coupon);

        if (!coupon.isIssuable()) {
            throw new IllegalStateException("발급 가능한 쿠폰이 아닙니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = calculateExpiredAt(coupon, now);

        coupon.incrementIssuedQuantity();

        return MemberCoupon.builder()
                .memberId(memberId)
                .couponId(coupon.getId())
                .status(MemberCouponStatus.AVAILABLE)
                .issuedAt(now)
                .expiredAt(expiredAt)
                .build();
    }

    public static MemberCoupon of(
            Long id,
            Long memberId,
            Long couponId,
            MemberCouponStatus status,
            LocalDateTime issuedAt,
            LocalDateTime expiredAt,
            LocalDateTime usedAt
    ) {
        return MemberCoupon.builder()
                .id(id)
                .memberId(memberId)
                .couponId(couponId)
                .status(status)
                .issuedAt(issuedAt)
                .expiredAt(expiredAt)
                .usedAt(usedAt)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private MemberCoupon(
            Long id,
            Long memberId,
            Long couponId,
            MemberCouponStatus status,
            LocalDateTime issuedAt,
            LocalDateTime expiredAt,
            LocalDateTime usedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.couponId = couponId;
        this.status = status;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
    }

    public void use() {
        if (status != MemberCouponStatus.AVAILABLE) {
            throw new IllegalStateException("사용 가능한 쿠폰이 아닙니다.");
        }
        if (LocalDateTime.now().isAfter(expiredAt)) {
            this.status = MemberCouponStatus.EXPIRED;
            throw new IllegalStateException("만료된 쿠폰입니다.");
        }
        this.status = MemberCouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    public void restore() {
        if (status != MemberCouponStatus.USED) {
            throw new IllegalStateException("사용된 쿠폰만 복원할 수 있습니다.");
        }
        this.status = MemberCouponStatus.AVAILABLE;
        this.usedAt = null;
    }

    public boolean isUsable() {
        return status == MemberCouponStatus.AVAILABLE
                && LocalDateTime.now().isBefore(expiredAt);
    }

    private static LocalDateTime calculateExpiredAt(Coupon coupon, LocalDateTime issuedAt) {
        if (coupon.getValidDays() != null) {
            return issuedAt.plusDays(coupon.getValidDays());
        }
        return coupon.getValidUntil();
    }

    private static void validateMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("회원 ID는 필수입니다.");
        }
    }

    private static void validateCoupon(Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("쿠폰 정보는 필수입니다.");
        }
    }
}
