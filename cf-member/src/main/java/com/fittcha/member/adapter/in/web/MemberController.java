package com.fittcha.member.adapter.in.web;

import com.fittcha.member.application.port.in.GetMemberUseCase;
import com.fittcha.member.application.port.in.RegisterMemberUseCase;
import com.fittcha.member.application.port.in.UpdateMemberUseCase;
import com.fittcha.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 Controller
 *
 * @RestController: JSON 응답하는 Controller
 * @RequestMapping: 기본 URL 경로
 * @Valid: Request DTO 검증 실행
 * @RequestBody: JSON → 객체 변환
 *
 *               UseCase 인터페이스에 의존
 *               → 구현체(Service) 몰라도 됨
 *               → Hexagonal 핵심!
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final GetMemberUseCase getMemberUseCase;
    private final UpdateMemberUseCase updateMemberUseCase;

    @PostMapping
    public ResponseEntity<Member> register(@Valid @RequestBody RegisterMemberRequest request) {
        Member member = registerMemberUseCase.register(request.toCommand());
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable Long id) {
        Member member = getMemberUseCase.getById(id);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateProfile(@PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequest request) {
        Member member = updateMemberUseCase.updateProfile(id, request.toCommand());
        return ResponseEntity.ok(member);
    }
}
