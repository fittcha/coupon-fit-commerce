package com.fittcha.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("소셜 회원을 생성할 수 있다.")
    void createSocialMember() {
        // given
        String email = "test@kakao.com";
        String name = "홍길동";
        LoginType loginType = LoginType.KAKAO;
        String socialId = "kakao_123456";

        // when
        Member member = Member.createSocialMember(email, name, loginType, socialId);

        // then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getLoginType()).isEqualTo(LoginType.KAKAO);
        assertThat(member.getSocialId()).isEqualTo(socialId);
        assertThat(member.getGrade()).isEqualTo(MemberGrade.ROOKIE);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getPassword()).isNull();
    }

    @Test
    @DisplayName("이메일 회원을 생성할 수 있다.")
    void createEmailMember() {
        // given
        String email = "test@email.com";
        String password = "password123";
        String name = "홍길동";

        // when
        Member member = Member.createEmailMember(email, password, name);

        // then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getLoginType()).isEqualTo(LoginType.EMAIL);
        assertThat(member.getGrade()).isEqualTo(MemberGrade.ROOKIE);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("이메일이 없으면 예외가 발생한다.")
    void createMemberWithoutEmail() {
        // when & then
        assertThatThrownBy(() -> Member.createEmailMember(null, "password", "홍길동"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 필수입니다.");
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않으면 예외가 발생한다.")
    void createMemberWithInvalidEmail() {
        // when & then
        assertThatThrownBy(() -> Member.createEmailMember("invalid-email", "password", "홍길동"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효한 이메일 형식이 아닙니다.");
    }

    @Test
    @DisplayName("이름이 없으면 예외가 발생한다.")
    void createMemberWithoutName() {
        // when & then
        assertThatThrownBy(() -> Member.createEmailMember("test@email.com", "password", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다.");
    }

    @Test
    @DisplayName("이메일 회원 생성 시 비밀번호가 없으면 예외가 발생한다.")
    void createEmailMemberWithoutPassword() {
        // when & then
        assertThatThrownBy(() -> Member.createEmailMember("test@email.com", null, "홍길동"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 필수입니다.");
    }

    @Test
    @DisplayName("소셜 회원 생성 시 소셜ID가 없으면 예외가 발생한다.")
    void createSocialMemberWithoutSocialId() {
        // when & then
        assertThatThrownBy(() -> Member.createSocialMember("test@kakao.com", "홍길동", LoginType.KAKAO, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("소셜 ID는 필수입니다.");
    }

    @Test
    @DisplayName("소셜 회원 생성 시 EMAIL 타입이면 예외가 발생한다.")
    void createSocialMemberWithEmailType() {
        // when & then
        assertThatThrownBy(() -> Member.createSocialMember("test@email.com", "홍길동", LoginType.EMAIL, "social123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("소셜 로그인 타입이 아닙니다.");
    }

    @Test
    @DisplayName("프로필을 수정할 수 있다.")
    void updateProfile() {
        // given
        Member member = Member.createEmailMember("test@email.com", "password", "홍길동");

        // when
        member.updateProfile("김철수", "010-1234-5678");

        // then
        assertThat(member.getName()).isEqualTo("김철수");
        assertThat(member.getPhone()).isEqualTo("010-1234-5678");
    }

    @Test
    @DisplayName("등급을 변경할 수 있다.")
    void changeGrade() {
        // given
        Member member = Member.createEmailMember("test@email.com", "password", "홍길동");
        assertThat(member.getGrade()).isEqualTo(MemberGrade.ROOKIE);

        // when
        member.changeGrade(MemberGrade.VIP);

        // then
        assertThat(member.getGrade()).isEqualTo(MemberGrade.VIP);
    }

    @Test
    @DisplayName("회원 탈퇴를 할 수 있다.")
    void withdraw() {
        // given
        Member member = Member.createEmailMember("test@email.com", "password", "홍길동");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);

        // when
        member.withdraw();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.WITHDRAWN);
    }
}
