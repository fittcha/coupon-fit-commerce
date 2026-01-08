package com.fittcha.member.application.service;

import com.fittcha.member.application.port.in.RegisterMemberCommand;
import com.fittcha.member.application.port.in.RegisterMemberUseCase;
import com.fittcha.member.application.port.out.LoadMemberPort;
import com.fittcha.member.application.port.out.SaveMemberPort;
import com.fittcha.member.domain.LoginType;
import com.fittcha.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterMemberService implements RegisterMemberUseCase {

    private final SaveMemberPort saveMemberPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    public Member register(RegisterMemberCommand command) {
        // 이메일 중복 확인
        if (loadMemberPort.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + command.getEmail());
        }

        // 회원 생성
        Member member;
        if (command.getLoginType() == LoginType.EMAIL) {
            member = Member.createEmailMember(
                    command.getEmail(),
                    command.getPassword(),
                    command.getName());
        } else {
            member = Member.createSocialMember(
                    command.getEmail(),
                    command.getName(),
                    command.getLoginType(),
                    command.getSocialId());
        }

        return saveMemberPort.save(member);
    }
}
