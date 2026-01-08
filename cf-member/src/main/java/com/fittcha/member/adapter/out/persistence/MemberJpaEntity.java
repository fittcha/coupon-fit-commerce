package com.fittcha.member.adapter.out.persistence;

import com.fittcha.member.domain.LoginType;
import com.fittcha.member.domain.MemberGrade;
import com.fittcha.member.domain.MemberStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 JPA Entity
 *
 * Hexagonal에서는 Domain과 Entity를 분리
 *
 * Domain (Member)
 * → 순수 비즈니스 로직
 * → JPA 의존성 없음
 *
 * Entity (MemberJpaEntity)
 * → DB 매핑용
 * → JPA 어노테이션 있음
 *
 * 분리하면:
 * → Domain이 인프라(JPA)에 의존 안 함
 * → DB 바꿔도 Domain 안 바뀜
 * → Hexagonal 핵심!
 */
@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberGrade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoginType loginType;

    @Column(length = 100)
    private String socialId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private MemberJpaEntity(String email, String password, String name, String phone,
            MemberGrade grade, MemberStatus status, LoginType loginType, String socialId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.status = status;
        this.loginType = loginType;
        this.socialId = socialId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfile(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateGrade(MemberGrade grade) {
        this.grade = grade;
        this.updatedAt = LocalDateTime.now();
    }
}
