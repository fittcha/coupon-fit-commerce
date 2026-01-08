package com.fittcha.member.application.port.in;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원 프로필 수정 Command
 */
@Getter
@Builder
public class UpdateMemberCommand {

    private final String name;
    private final String phone;
}
