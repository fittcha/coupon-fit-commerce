package com.fittcha.coupon.domain;

/**
 * 쿠폰 타입
 * - PRODUCT: 상품 쿠폰 (특정 상품에 적용)
 * - CART: 장바구니 쿠폰 (주문 전체에 적용)
 */
public enum CouponType {
    PRODUCT,
    CART
}
