package com.fittcha.coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private Long id;
    private String name;
    private String description;
    private CouponType couponType;
    private DiscountType discountType;
    private int discountValue;
    private Integer maxDiscount;
    private IssueType issueType;
    private Integer totalQuantity;
    private int issuedQuantity;
    private LocalDateTime issueStartAt;
    private LocalDateTime issueEndAt;
    private Integer validDays;
    private LocalDateTime validUntil;
    private List<CouponCondition> conditions = new ArrayList<>();

    public static Coupon create(
            String name,
            String description,
            CouponType couponType,
            DiscountType discountType,
            int discountValue,
            Integer maxDiscount,
            IssueType issueType,
            Integer totalQuantity,
            LocalDateTime issueStartAt,
            LocalDateTime issueEndAt,
            Integer validDays,
            LocalDateTime validUntil
    ) {
        validateName(name);
        validateDiscountValue(discountValue);
        validateIssuePeriod(issueStartAt, issueEndAt);
        validateValidPeriod(validDays, validUntil);

        return Coupon.builder()
                .name(name)
                .description(description)
                .couponType(couponType)
                .discountType(discountType)
                .discountValue(discountValue)
                .maxDiscount(maxDiscount)
                .issueType(issueType)
                .totalQuantity(totalQuantity)
                .issuedQuantity(0)
                .issueStartAt(issueStartAt)
                .issueEndAt(issueEndAt)
                .validDays(validDays)
                .validUntil(validUntil)
                .conditions(new ArrayList<>())
                .build();
    }

    public static Coupon of(
            Long id,
            String name,
            String description,
            CouponType couponType,
            DiscountType discountType,
            int discountValue,
            Integer maxDiscount,
            IssueType issueType,
            Integer totalQuantity,
            int issuedQuantity,
            LocalDateTime issueStartAt,
            LocalDateTime issueEndAt,
            Integer validDays,
            LocalDateTime validUntil,
            List<CouponCondition> conditions
    ) {
        return Coupon.builder()
                .id(id)
                .name(name)
                .description(description)
                .couponType(couponType)
                .discountType(discountType)
                .discountValue(discountValue)
                .maxDiscount(maxDiscount)
                .issueType(issueType)
                .totalQuantity(totalQuantity)
                .issuedQuantity(issuedQuantity)
                .issueStartAt(issueStartAt)
                .issueEndAt(issueEndAt)
                .validDays(validDays)
                .validUntil(validUntil)
                .conditions(conditions != null ? conditions : new ArrayList<>())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Coupon(
            Long id,
            String name,
            String description,
            CouponType couponType,
            DiscountType discountType,
            int discountValue,
            Integer maxDiscount,
            IssueType issueType,
            Integer totalQuantity,
            int issuedQuantity,
            LocalDateTime issueStartAt,
            LocalDateTime issueEndAt,
            Integer validDays,
            LocalDateTime validUntil,
            List<CouponCondition> conditions
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.couponType = couponType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscount = maxDiscount;
        this.issueType = issueType;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
        this.issueStartAt = issueStartAt;
        this.issueEndAt = issueEndAt;
        this.validDays = validDays;
        this.validUntil = validUntil;
        this.conditions = conditions;
    }

    public void update(
            String name,
            String description,
            CouponType couponType,
            DiscountType discountType,
            int discountValue,
            Integer maxDiscount,
            IssueType issueType,
            Integer totalQuantity,
            LocalDateTime issueStartAt,
            LocalDateTime issueEndAt,
            Integer validDays,
            LocalDateTime validUntil
    ) {
        validateName(name);
        validateDiscountValue(discountValue);
        validateIssuePeriod(issueStartAt, issueEndAt);
        validateValidPeriod(validDays, validUntil);

        this.name = name;
        this.description = description;
        this.couponType = couponType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscount = maxDiscount;
        this.issueType = issueType;
        this.totalQuantity = totalQuantity;
        this.issueStartAt = issueStartAt;
        this.issueEndAt = issueEndAt;
        this.validDays = validDays;
        this.validUntil = validUntil;
    }

    public void addCondition(CouponCondition condition) {
        this.conditions.add(condition);
    }

    public boolean isIssuable() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(issueStartAt) || now.isAfter(issueEndAt)) {
            return false;
        }
        if (totalQuantity != null && issuedQuantity >= totalQuantity) {
            return false;
        }
        return true;
    }

    public void incrementIssuedQuantity() {
        this.issuedQuantity++;
    }

    public int calculateDiscount(int orderAmount) {
        int discount;
        if (discountType == DiscountType.FIXED) {
            discount = discountValue;
        } else {
            discount = orderAmount * discountValue / 100;
        }

        if (maxDiscount != null && discount > maxDiscount) {
            discount = maxDiscount;
        }

        return Math.min(discount, orderAmount);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰명은 필수입니다.");
        }
    }

    private static void validateDiscountValue(int discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException("할인 값은 0보다 커야 합니다.");
        }
    }

    private static void validateIssuePeriod(LocalDateTime issueStartAt, LocalDateTime issueEndAt) {
        if (issueStartAt == null || issueEndAt == null) {
            throw new IllegalArgumentException("발급 기간은 필수입니다.");
        }
        if (issueStartAt.isAfter(issueEndAt)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    private static void validateValidPeriod(Integer validDays, LocalDateTime validUntil) {
        if (validDays == null && validUntil == null) {
            throw new IllegalArgumentException("유효일수 또는 만료일 중 하나는 필수입니다.");
        }
    }
}
