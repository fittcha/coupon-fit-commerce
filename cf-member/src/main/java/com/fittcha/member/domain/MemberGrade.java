package com.fittcha.member.domain;

/**
 * 회원 등급
 * - ROOKIE: 신규 가입 (기본값)
 * - FAMILY: 누적 구매 10만원 이상
 * - VIP: 누적 구매 50만원 이상
 */
public enum MemberGrade {
    ROOKIE,
    FAMILY,
    VIP
}
