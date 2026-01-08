package com.fittcha.member.application.port.out;

import com.fittcha.member.domain.Member;

/**
 * 회원 저장 Port Out
 */
public interface SaveMemberPort {

    /**
     * 회원 저장
     */
    Member save(Member member);
}
