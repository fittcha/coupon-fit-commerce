package com.fittcha.member.application.service;

import com.fittcha.member.application.port.in.UpdateMemberCommand;
import com.fittcha.member.application.port.in.UpdateMemberUseCase;
import com.fittcha.member.application.port.out.LoadMemberPort;
import com.fittcha.member.application.port.out.UpdateMemberPort;
import com.fittcha.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService implements UpdateMemberUseCase {

    private final LoadMemberPort loadMemberPort;
    private final UpdateMemberPort updateMemberPort;

    @Override
    public Member updateProfile(Long id, UpdateMemberCommand command) {
        Member member = loadMemberPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + id));

        member.updateProfile(command.getName(), command.getPhone());

        return updateMemberPort.update(member);
    }
}
