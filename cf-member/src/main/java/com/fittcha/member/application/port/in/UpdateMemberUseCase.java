package com.fittcha.member.application.port.in;

import com.fittcha.member.domain.Member;

/**
 * 회원 수정 Use Case
 */
public interface UpdateMemberUseCase {

    /**
     * 회원 프로필 수정
     */
    Member updateProfile(Long id, UpdateMemberCommand command);
}
