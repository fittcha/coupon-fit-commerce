package com.fittcha.member.application.service;

import com.fittcha.member.application.port.in.GetMemberUseCase;
import com.fittcha.member.application.port.out.LoadMemberPort;
import com.fittcha.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberService implements GetMemberUseCase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public Member getById(Long id) {
        return loadMemberPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + id));
    }

    @Override
    public Optional<Member> getByEmail(String email) {
        return loadMemberPort.findByEmail(email);
    }
}
