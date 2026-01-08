package com.fittcha.member.application.port.out;

import com.fittcha.member.domain.Member;

/**
 * 회원 수정 Port Out
 */
public interface UpdateMemberPort {

    /**
     * 회원 수정
     */
    Member update(Member member);
}
