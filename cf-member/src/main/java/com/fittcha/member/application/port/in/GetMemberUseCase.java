package com.fittcha.member.application.port.in;

import com.fittcha.member.domain.Member;

import java.util.Optional;

/**
 * 회원 조회 Use Case
 */
public interface GetMemberUseCase {

    /**
     * ID로 회원 조회
     */
    Member getById(Long id);

    /**
     * 이메일로 회원 조회
     */
    Optional<Member> getByEmail(String email);
}
