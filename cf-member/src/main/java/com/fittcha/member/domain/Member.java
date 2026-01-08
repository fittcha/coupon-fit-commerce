package com.fittcha.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 도메인 모델
 *
 * 1. @Getter만 사용 (@Setter 없음)
 * → 외부에서 함부로 값 변경 못하게
 * → 불변성 유지
 *
 * 2. 생성자 private + 정적 팩토리 메서드 (create)
 * → 생성 로직을 한 곳에서 관리
 * → grade는 항상 ROOKIE, status는 항상 ACTIVE로 시작
 *
 * 3. NoArgsConstructor(PROTECTED)
 * → JPA용 기본 생성자 (나중에 엔티티 만들 때)
 * → protected로 외부에서 빈 객체 생성 막음
 *
 * 4. @Builder(access = AccessLevel.PRIVATE)
 * → 외부에서 builder() 직접 호출 막고, create() 메서드로만 생성하도록 강제
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private MemberGrade grade;
    private MemberStatus status;
    private LoginType loginType;
    private String socialId;

    /**
     * 회원 생성 - 소셜 로그인용
     */
    public static Member createSocialMember(String email, String name, LoginType loginType, String socialId) {
        validateEmail(email);
        validateName(name);
        validateLoginType(loginType);

        if (loginType == LoginType.EMAIL) {
            throw new IllegalArgumentException("소셜 로그인 타입이 아닙니다.");
        }
        if (socialId == null || socialId.isBlank()) {
            throw new IllegalArgumentException("소셜 ID는 필수입니다.");
        }

        return Member.builder()
                .email(email)
                .password(null)
                .name(name)
                .phone(null)
                .grade(MemberGrade.ROOKIE)
                .status(MemberStatus.ACTIVE)
                .loginType(loginType)
                .socialId(socialId)
                .build();
    }

    /**
     * 회원 생성 - 이메일 가입용
     */
    public static Member createEmailMember(String email, String password, String name) {
        validateEmail(email);
        validateName(name);

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(null)
                .grade(MemberGrade.ROOKIE)
                .status(MemberStatus.ACTIVE)
                .loginType(LoginType.EMAIL)
                .socialId(null)
                .build();
    }

    /**
     * 영속성에서 도메인으로 복원할 때 사용
     */
    public static Member of(Long id, String email, String password, String name, String phone,
            MemberGrade grade, MemberStatus status, LoginType loginType, String socialId) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .grade(grade)
                .status(status)
                .loginType(loginType)
                .socialId(socialId)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Member(Long id, String email, String password, String name, String phone,
            MemberGrade grade, MemberStatus status, LoginType loginType, String socialId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.status = status;
        this.loginType = loginType;
        this.socialId = socialId;
    }

    /**
     * 프로필 수정
     */
    public void updateProfile(String name, String phone) {
        validateName(name);
        this.name = name;
        this.phone = phone;
    }

    /**
     * 등급 변경
     */
    public void changeGrade(MemberGrade newGrade) {
        if (newGrade == null) {
            throw new IllegalArgumentException("등급은 필수입니다.");
        }
        this.grade = newGrade;
    }

    /**
     * 회원 탈퇴
     */
    public void withdraw() {
        this.status = MemberStatus.WITHDRAWN;
    }

    // === 유효성 검증 메서드 ===

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("유효한 이메일 형식이 아닙니다.");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
    }

    private static void validateLoginType(LoginType loginType) {
        if (loginType == null) {
            throw new IllegalArgumentException("로그인 타입은 필수입니다.");
        }
    }
}
