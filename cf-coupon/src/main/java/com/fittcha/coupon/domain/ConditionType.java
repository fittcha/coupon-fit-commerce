package com.fittcha.coupon.domain;

/**
 * 쿠폰 적용 조건 타입
 * - BRAND: 특정 브랜드만 (condition_value: brand_id)
 * - CATEGORY: 특정 카테고리만 (condition_value: category_id)
 * - PRODUCT: 특정 상품만 (condition_value: product_id)
 * - GRADE: 특정 등급 이상 (condition_value: "VIP")
 * - MIN_ORDER: 최소 주문금액 (condition_value: "50000")
 * - MAX_ORDER: 최대 주문금액 (condition_value: "200000")
 * - FIRST_ORDER: 첫 주문만 (condition_value: "true")
 */
public enum ConditionType {
    BRAND,
    CATEGORY,
    PRODUCT,
    GRADE,
    MIN_ORDER,
    MAX_ORDER,
    FIRST_ORDER
}
