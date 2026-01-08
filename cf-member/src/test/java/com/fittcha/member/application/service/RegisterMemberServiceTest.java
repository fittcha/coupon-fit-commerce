package com.fittcha.member.application.service;

import com.fittcha.member.application.port.in.RegisterMemberCommand;
import com.fittcha.member.application.port.out.LoadMemberPort;
import com.fittcha.member.application.port.out.SaveMemberPort;
import com.fittcha.member.domain.LoginType;
import com.fittcha.member.domain.Member;
import com.fittcha.member.domain.MemberGrade;
import com.fittcha.member.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * 회원 등록 서비스 단위 테스트
 *
 * @ExtendWith(MockitoExtension.class)
 * → Mockito 사용 설정
 *
 * @Mock SaveMemberPort
 *       → 가짜 DB (실제 DB 없이 테스트)
 *
 * @InjectMocks RegisterMemberService
 *              → Mock을 주입받은 Service
 */
@ExtendWith(MockitoExtension.class)
class RegisterMemberServiceTest {

    @InjectMocks
    private RegisterMemberService registerMemberService;

    @Mock
    private SaveMemberPort saveMemberPort;

    @Mock
    private LoadMemberPort loadMemberPort;

    @Test
    @DisplayName("소셜 회원을 등록할 수 있다.")
    void registerSocialMember() {
        // given
        RegisterMemberCommand command = RegisterMemberCommand.builder()
                .email("test@kakao.com")
                .name("홍길동")
                .loginType(LoginType.KAKAO)
                .socialId("kakao_123456")
                .build();

        given(loadMemberPort.existsByEmail(command.getEmail())).willReturn(false);
        given(saveMemberPort.save(any(Member.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        Member result = registerMemberService.register(command);

        // then
        assertThat(result.getEmail()).isEqualTo("test@kakao.com");
        assertThat(result.getName()).isEqualTo("홍길동");
        assertThat(result.getLoginType()).isEqualTo(LoginType.KAKAO);
        assertThat(result.getGrade()).isEqualTo(MemberGrade.ROOKIE);
        assertThat(result.getStatus()).isEqualTo(MemberStatus.ACTIVE);

        verify(loadMemberPort).existsByEmail(command.getEmail());
        verify(saveMemberPort).save(any(Member.class));
    }

    @Test
    @DisplayName("이메일 회원을 등록할 수 있다.")
    void registerEmailMember() {
        // given
        RegisterMemberCommand command = RegisterMemberCommand.builder()
                .email("test@email.com")
                .password("password123")
                .name("홍길동")
                .loginType(LoginType.EMAIL)
                .build();

        given(loadMemberPort.existsByEmail(command.getEmail())).willReturn(false);
        given(saveMemberPort.save(any(Member.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        Member result = registerMemberService.register(command);

        // then
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        assertThat(result.getLoginType()).isEqualTo(LoginType.EMAIL);

        verify(saveMemberPort).save(any(Member.class));
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 등록하면 예외가 발생한다.")
    void registerWithDuplicateEmail() {
        // given
        RegisterMemberCommand command = RegisterMemberCommand.builder()
                .email("existing@email.com")
                .password("password123")
                .name("홍길동")
                .loginType(LoginType.EMAIL)
                .build();

        given(loadMemberPort.existsByEmail(command.getEmail())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> registerMemberService.register(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다: existing@email.com");
    }
}
