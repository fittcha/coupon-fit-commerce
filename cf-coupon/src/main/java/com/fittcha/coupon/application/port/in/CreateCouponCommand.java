package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CreateCouponCommand {

    private final String name;
    private final String description;
    private final CouponType couponType;
    private final DiscountType discountType;
    private final int discountValue;
    private final Integer maxDiscount;
    private final IssueType issueType;
    private final Integer totalQuantity;
    private final LocalDateTime issueStartAt;
    private final LocalDateTime issueEndAt;
    private final Integer validDays;
    private final LocalDateTime validUntil;
    private final List<ConditionCommand> conditions;

    @Builder
    @Getter
    public static class ConditionCommand {
        private final String conditionType;
        private final String conditionValue;
    }
}
