package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    @Column(nullable = false)
    private int discountValue;

    private Integer maxDiscount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IssueType issueType;

    private Integer totalQuantity;

    @Column(nullable = false)
    private int issuedQuantity;

    @Column(nullable = false)
    private LocalDateTime issueStartAt;

    @Column(nullable = false)
    private LocalDateTime issueEndAt;

    private Integer validDays;

    private LocalDateTime validUntil;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CouponConditionJpaEntity> conditions = new ArrayList<>();

    private Long createdBy;

    private Long updatedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private CouponJpaEntity(
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
            Long createdBy,
            Long updatedBy
    ) {
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
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementIssuedQuantity() {
        this.issuedQuantity++;
        this.updatedAt = LocalDateTime.now();
    }

    public void addCondition(CouponConditionJpaEntity condition) {
        this.conditions.add(condition);
        condition.setCoupon(this);
    }
}
