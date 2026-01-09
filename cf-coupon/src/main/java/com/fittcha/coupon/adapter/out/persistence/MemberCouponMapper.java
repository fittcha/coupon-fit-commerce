package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.domain.MemberCoupon;
import org.springframework.stereotype.Component;

@Component
public class MemberCouponMapper {

    public MemberCouponJpaEntity toJpaEntity(MemberCoupon memberCoupon) {
        return MemberCouponJpaEntity.builder()
                .memberId(memberCoupon.getMemberId())
                .couponId(memberCoupon.getCouponId())
                .status(memberCoupon.getStatus())
                .issuedAt(memberCoupon.getIssuedAt())
                .expiredAt(memberCoupon.getExpiredAt())
                .usedAt(memberCoupon.getUsedAt())
                .build();
    }

    public MemberCoupon toDomain(MemberCouponJpaEntity entity) {
        return MemberCoupon.of(
                entity.getId(),
                entity.getMemberId(),
                entity.getCouponId(),
                entity.getStatus(),
                entity.getIssuedAt(),
                entity.getExpiredAt(),
                entity.getUsedAt()
        );
    }
}
