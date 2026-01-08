package com.fittcha.member.application.port.out;

import com.fittcha.member.domain.Member;

import java.util.Optional;

/**
 * 회원 조회 Port Out
 */
public interface LoadMemberPort {

    /**
     * ID로 회원 조회
     */
    Optional<Member> findById(Long id);

    /**
     * 이메일로 회원 조회
     */
    Optional<Member> findByEmail(String email);

    /**
     * 이메일 중복 확인
     */
    boolean existsByEmail(String email);
}
