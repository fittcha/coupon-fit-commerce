package com.fittcha.coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponCondition {

    private Long id;
    private Long couponId;
    private ConditionType conditionType;
    private String conditionValue;

    public static CouponCondition create(Long couponId, ConditionType conditionType, String conditionValue) {
        validateConditionType(conditionType);
        validateConditionValue(conditionValue);

        return CouponCondition.builder()
                .couponId(couponId)
                .conditionType(conditionType)
                .conditionValue(conditionValue)
                .build();
    }

    public static CouponCondition of(Long id, Long couponId, ConditionType conditionType, String conditionValue) {
        return CouponCondition.builder()
                .id(id)
                .couponId(couponId)
                .conditionType(conditionType)
                .conditionValue(conditionValue)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private CouponCondition(Long id, Long couponId, ConditionType conditionType, String conditionValue) {
        this.id = id;
        this.couponId = couponId;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
    }

    private static void validateConditionType(ConditionType conditionType) {
        if (conditionType == null) {
            throw new IllegalArgumentException("조건 타입은 필수입니다.");
        }
    }

    private static void validateConditionValue(String conditionValue) {
        if (conditionValue == null || conditionValue.isBlank()) {
            throw new IllegalArgumentException("조건 값은 필수입니다.");
        }
    }
}
