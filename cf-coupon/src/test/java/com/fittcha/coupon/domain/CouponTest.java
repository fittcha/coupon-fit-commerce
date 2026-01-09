package com.fittcha.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @Test
    @DisplayName("쿠폰을 생성할 수 있다")
    void createCoupon() {
        // given
        String name = "신규 회원 10% 할인";
        String description = "신규 가입 회원 전용 쿠폰";
        CouponType couponType = CouponType.CART;
        DiscountType discountType = DiscountType.RATE;
        int discountValue = 10;
        Integer maxDiscount = 5000;
        IssueType issueType = IssueType.DOWNLOAD;
        Integer totalQuantity = 1000;
        LocalDateTime issueStartAt = LocalDateTime.now();
        LocalDateTime issueEndAt = LocalDateTime.now().plusDays(30);
        Integer validDays = 7;

        // when
        Coupon coupon = Coupon.create(
                name, description, couponType, discountType, discountValue,
                maxDiscount, issueType, totalQuantity, issueStartAt, issueEndAt,
                validDays, null
        );

        // then
        assertThat(coupon.getName()).isEqualTo(name);
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.CART);
        assertThat(coupon.getDiscountType()).isEqualTo(DiscountType.RATE);
        assertThat(coupon.getDiscountValue()).isEqualTo(10);
        assertThat(coupon.getIssuedQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("쿠폰명이 없으면 예외가 발생한다")
    void createCouponWithoutName() {
        // given & when & then
        assertThatThrownBy(() -> Coupon.create(
                null, "설명", CouponType.CART, DiscountType.RATE, 10,
                5000, IssueType.DOWNLOAD, 1000,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰명은 필수입니다.");
    }

    @Test
    @DisplayName("할인 값이 0 이하면 예외가 발생한다")
    void createCouponWithZeroDiscount() {
        // given & when & then
        assertThatThrownBy(() -> Coupon.create(
                "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 0,
                5000, IssueType.DOWNLOAD, 1000,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 값은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("발급 시작일이 종료일보다 이후면 예외가 발생한다")
    void createCouponWithInvalidIssuePeriod() {
        // given & when & then
        assertThatThrownBy(() -> Coupon.create(
                "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 10,
                5000, IssueType.DOWNLOAD, 1000,
                LocalDateTime.now().plusDays(30), LocalDateTime.now(),
                7, null
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 종료일보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("유효일수와 만료일이 모두 없으면 예외가 발생한다")
    void createCouponWithoutValidPeriod() {
        // given & when & then
        assertThatThrownBy(() -> Coupon.create(
                "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 10,
                5000, IssueType.DOWNLOAD, 1000,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                null, null
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효일수 또는 만료일 중 하나는 필수입니다.");
    }

    @Test
    @DisplayName("정액 할인 금액을 계산한다")
    void calculateFixedDiscount() {
        // given
        Coupon coupon = Coupon.of(
                1L, "5000원 할인", "설명", CouponType.CART, DiscountType.FIXED, 5000,
                null, IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        // when
        int discount = coupon.calculateDiscount(30000);

        // then
        assertThat(discount).isEqualTo(5000);
    }

    @Test
    @DisplayName("정률 할인 금액을 계산한다")
    void calculateRateDiscount() {
        // given
        Coupon coupon = Coupon.of(
                1L, "10% 할인", "설명", CouponType.CART, DiscountType.RATE, 10,
                null, IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        // when
        int discount = coupon.calculateDiscount(30000);

        // then
        assertThat(discount).isEqualTo(3000);
    }

    @Test
    @DisplayName("정률 할인시 최대 할인 금액을 초과하지 않는다")
    void calculateRateDiscountWithMaxLimit() {
        // given
        Coupon coupon = Coupon.of(
                1L, "10% 할인 (최대 2000원)", "설명", CouponType.CART, DiscountType.RATE, 10,
                2000, IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        // when
        int discount = coupon.calculateDiscount(30000);

        // then
        assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("발급 기간 내에 있고 수량이 남아있으면 발급 가능하다")
    void isIssuableWhenValid() {
        // given
        Coupon coupon = Coupon.of(
                1L, "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 10,
                null, IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        // when & then
        assertThat(coupon.isIssuable()).isTrue();
    }

    @Test
    @DisplayName("발급 기간이 지나면 발급 불가능하다")
    void isNotIssuableWhenExpired() {
        // given
        Coupon coupon = Coupon.of(
                1L, "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 10,
                null, IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(1),
                7, null, null
        );

        // when & then
        assertThat(coupon.isIssuable()).isFalse();
    }

    @Test
    @DisplayName("발급 수량이 다 차면 발급 불가능하다")
    void isNotIssuableWhenSoldOut() {
        // given
        Coupon coupon = Coupon.of(
                1L, "테스트 쿠폰", "설명", CouponType.CART, DiscountType.RATE, 10,
                null, IssueType.DOWNLOAD, 100, 100,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        // when & then
        assertThat(coupon.isIssuable()).isFalse();
    }
}
