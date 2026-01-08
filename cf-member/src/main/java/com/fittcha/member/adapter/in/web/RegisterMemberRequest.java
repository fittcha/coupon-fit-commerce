package com.fittcha.member.adapter.in.web;

import com.fittcha.member.application.port.in.RegisterMemberCommand;
import com.fittcha.member.domain.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 등록 Request DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class RegisterMemberRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    private String loginType;

    private String socialId;

    public RegisterMemberCommand toCommand() {
        return RegisterMemberCommand.builder()
                .email(email)
                .password(password)
                .name(name)
                .loginType(loginType != null ? LoginType.valueOf(loginType) : LoginType.EMAIL)
                .socialId(socialId)
                .build();
    }
}
