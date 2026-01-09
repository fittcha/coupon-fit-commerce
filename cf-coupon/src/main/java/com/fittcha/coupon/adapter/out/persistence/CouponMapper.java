package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.CouponCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponMapper {

    public CouponJpaEntity toJpaEntity(Coupon coupon) {
        return CouponJpaEntity.builder()
                .name(coupon.getName())
                .description(coupon.getDescription())
                .couponType(coupon.getCouponType())
                .discountType(coupon.getDiscountType())
                .discountValue(coupon.getDiscountValue())
                .maxDiscount(coupon.getMaxDiscount())
                .issueType(coupon.getIssueType())
                .totalQuantity(coupon.getTotalQuantity())
                .issuedQuantity(coupon.getIssuedQuantity())
                .issueStartAt(coupon.getIssueStartAt())
                .issueEndAt(coupon.getIssueEndAt())
                .validDays(coupon.getValidDays())
                .validUntil(coupon.getValidUntil())
                .build();
    }

    public Coupon toDomain(CouponJpaEntity entity) {
        List<CouponCondition> conditions = entity.getConditions().stream()
                .map(this::toConditionDomain)
                .toList();

        return Coupon.of(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCouponType(),
                entity.getDiscountType(),
                entity.getDiscountValue(),
                entity.getMaxDiscount(),
                entity.getIssueType(),
                entity.getTotalQuantity(),
                entity.getIssuedQuantity(),
                entity.getIssueStartAt(),
                entity.getIssueEndAt(),
                entity.getValidDays(),
                entity.getValidUntil(),
                conditions
        );
    }

    public CouponConditionJpaEntity toConditionJpaEntity(CouponCondition condition) {
        return CouponConditionJpaEntity.builder()
                .conditionType(condition.getConditionType())
                .conditionValue(condition.getConditionValue())
                .build();
    }

    public CouponCondition toConditionDomain(CouponConditionJpaEntity entity) {
        return CouponCondition.of(
                entity.getId(),
                entity.getCoupon().getId(),
                entity.getConditionType(),
                entity.getConditionValue()
        );
    }
}
