package com.fittcha.member.adapter.in.web;

import com.fittcha.member.application.port.in.UpdateMemberCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 수정 Request DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateMemberRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    private String phone;

    public UpdateMemberCommand toCommand() {
        return UpdateMemberCommand.builder()
                .name(name)
                .phone(phone)
                .build();
    }
}
