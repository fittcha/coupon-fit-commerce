package com.fittcha.member.application.port.in;

import com.fittcha.member.domain.LoginType;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원 등록 Command
 */
@Getter
@Builder
public class RegisterMemberCommand {

    private final String email;
    private final String password;
    private final String name;
    private final LoginType loginType;
    private final String socialId;
}
