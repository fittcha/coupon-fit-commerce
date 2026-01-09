package com.fittcha.coupon.domain;

/**
 * 발급 타입
 * - DOWNLOAD: 다운로드 발급 (클릭 시 발급)
 * - AUTO: 자동 발급 (웰컴 쿠폰, 등급 쿠폰 등)
 */
public enum IssueType {
    DOWNLOAD,
    AUTO
}
