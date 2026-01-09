package com.fittcha.coupon.adapter.in.web;

import com.fittcha.coupon.application.port.in.CreateCouponCommand;
import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateCouponRequest {

    @NotBlank(message = "쿠폰명은 필수입니다")
    private String name;

    private String description;

    @NotNull(message = "쿠폰 타입은 필수입니다")
    private CouponType couponType;

    @NotNull(message = "할인 타입은 필수입니다")
    private DiscountType discountType;

    @Min(value = 1, message = "할인 값은 1 이상이어야 합니다")
    private int discountValue;

    private Integer maxDiscount;

    @NotNull(message = "발급 타입은 필수입니다")
    private IssueType issueType;

    private Integer totalQuantity;

    @NotNull(message = "발급 시작일은 필수입니다")
    private LocalDateTime issueStartAt;

    @NotNull(message = "발급 종료일은 필수입니다")
    private LocalDateTime issueEndAt;

    private Integer validDays;

    private LocalDateTime validUntil;

    private List<ConditionRequest> conditions;

    @Getter
    @NoArgsConstructor
    public static class ConditionRequest {
        private String conditionType;
        private String conditionValue;
    }

    public CreateCouponCommand toCommand() {
        List<CreateCouponCommand.ConditionCommand> conditionCommands = null;
        if (conditions != null) {
            conditionCommands = conditions.stream()
                    .map(c -> CreateCouponCommand.ConditionCommand.builder()
                            .conditionType(c.getConditionType())
                            .conditionValue(c.getConditionValue())
                            .build())
                    .toList();
        }

        return CreateCouponCommand.builder()
                .name(name)
                .description(description)
                .couponType(couponType)
                .discountType(discountType)
                .discountValue(discountValue)
                .maxDiscount(maxDiscount)
                .issueType(issueType)
                .totalQuantity(totalQuantity)
                .issueStartAt(issueStartAt)
                .issueEndAt(issueEndAt)
                .validDays(validDays)
                .validUntil(validUntil)
                .conditions(conditionCommands)
                .build();
    }
}
