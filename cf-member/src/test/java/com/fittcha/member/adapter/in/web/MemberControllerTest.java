package com.fittcha.member.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fittcha.member.application.port.in.GetMemberUseCase;
import com.fittcha.member.application.port.in.RegisterMemberCommand;
import com.fittcha.member.application.port.in.RegisterMemberUseCase;
import com.fittcha.member.application.port.in.UpdateMemberUseCase;
import com.fittcha.member.domain.LoginType;
import com.fittcha.member.domain.Member;
import com.fittcha.member.domain.MemberGrade;
import com.fittcha.member.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 회원 Controller API 테스트
 *
 * @WebMvcTest → Controller만 테스트 (가벼움)
 * @MockitoBean → UseCase를 가짜로 대체
 *              MockMvc → HTTP 요청 시뮬레이션
 *              jsonPath → JSON 응답 검증
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterMemberUseCase registerMemberUseCase;

    @MockitoBean
    private GetMemberUseCase getMemberUseCase;

    @MockitoBean
    private UpdateMemberUseCase updateMemberUseCase;

    @Test
    @DisplayName("회원을 등록할 수 있다.")
    void registerMember() throws Exception {
        // given
        Member member = Member.of(1L, "test@email.com", "password", "홍길동", null,
                MemberGrade.ROOKIE, MemberStatus.ACTIVE, LoginType.EMAIL, null);

        given(registerMemberUseCase.register(any(RegisterMemberCommand.class)))
                .willReturn(member);

        Map<String, Object> request = Map.of(
                "email", "test@email.com",
                "password", "password123",
                "name", "홍길동",
                "loginType", "EMAIL");

        // when & then
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.grade").value("ROOKIE"));
    }

    @Test
    @DisplayName("회원을 조회할 수 있다.")
    void getMember() throws Exception {
        // given
        Member member = Member.of(1L, "test@email.com", "password", "홍길동", "010-1234-5678",
                MemberGrade.FAMILY, MemberStatus.ACTIVE, LoginType.EMAIL, null);

        given(getMemberUseCase.getById(1L)).willReturn(member);

        // when & then
        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.phone").value("010-1234-5678"))
                .andExpect(jsonPath("$.grade").value("FAMILY"));
    }

    @Test
    @DisplayName("회원 프로필을 수정할 수 있다.")
    void updateMemberProfile() throws Exception {
        // given
        Member member = Member.of(1L, "test@email.com", "password", "김철수", "010-9999-8888",
                MemberGrade.ROOKIE, MemberStatus.ACTIVE, LoginType.EMAIL, null);

        given(updateMemberUseCase.updateProfile(eq(1L), any())).willReturn(member);

        Map<String, Object> request = Map.of(
                "name", "김철수",
                "phone", "010-9999-8888");

        // when & then
        mockMvc.perform(put("/api/members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김철수"))
                .andExpect(jsonPath("$.phone").value("010-9999-8888"));
    }

    @Test
    @DisplayName("회원 등록 시 이메일이 없으면 400 에러가 발생한다.")
    void registerMemberWithoutEmail() throws Exception {
        // given
        Map<String, Object> request = Map.of(
                "password", "password123",
                "name", "홍길동");

        // when & then
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("프로필 수정 시 이름이 없으면 400 에러가 발생한다.")
    void updateProfileWithoutName() throws Exception {
        // given
        Map<String, Object> request = Map.of(
                "phone", "010-1234-5678");

        // when & then
        mockMvc.perform(put("/api/members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
