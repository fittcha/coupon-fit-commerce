package com.fittcha.member.application.port.in;

import com.fittcha.member.domain.Member;

/**
 * 회원 등록 Use Case
 */
public interface RegisterMemberUseCase {

    /**
     * 회원 등록
     */
    Member register(RegisterMemberCommand command);
}
