package com.fittcha.coupon.application.port.in;

import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UpdateCouponCommand {

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
}
