package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.domain.ConditionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_condition")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponConditionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponJpaEntity coupon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConditionType conditionType;

    @Column(nullable = false, length = 100)
    private String conditionValue;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private CouponConditionJpaEntity(ConditionType conditionType, String conditionValue) {
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    void setCoupon(CouponJpaEntity coupon) {
        this.coupon = coupon;
    }
}
