package com.fittcha.coupon.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fittcha.coupon.application.port.in.*;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateCouponUseCase createCouponUseCase;

    @MockitoBean
    private GetCouponUseCase getCouponUseCase;

    @MockitoBean
    private UpdateCouponUseCase updateCouponUseCase;

    @MockitoBean
    private DeleteCouponUseCase deleteCouponUseCase;

    @MockitoBean
    private IssueCouponUseCase issueCouponUseCase;

    @MockitoBean
    private GetMemberCouponUseCase getMemberCouponUseCase;

    @Test
    @DisplayName("쿠폰을 생성할 수 있다")
    void createCoupon() throws Exception {
        // given
        Coupon coupon = Coupon.of(
                1L, "신규 회원 10% 할인", "신규 가입 회원 전용 쿠폰",
                CouponType.CART, DiscountType.RATE, 10, 5000,
                IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        given(createCouponUseCase.create(any(CreateCouponCommand.class)))
                .willReturn(coupon);

        String requestJson = """
                {
                    "name": "신규 회원 10% 할인",
                    "description": "신규 가입 회원 전용 쿠폰",
                    "couponType": "CART",
                    "discountType": "RATE",
                    "discountValue": 10,
                    "maxDiscount": 5000,
                    "issueType": "DOWNLOAD",
                    "totalQuantity": 1000,
                    "issueStartAt": "2025-01-01T00:00:00",
                    "issueEndAt": "2025-01-31T23:59:59",
                    "validDays": 7
                }
                """;

        // when & then
        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("신규 회원 10% 할인"))
                .andExpect(jsonPath("$.discountType").value("RATE"))
                .andExpect(jsonPath("$.discountValue").value(10));
    }

    @Test
    @DisplayName("쿠폰을 조회할 수 있다")
    void getCoupon() throws Exception {
        // given
        Coupon coupon = Coupon.of(
                1L, "테스트 쿠폰", "설명",
                CouponType.CART, DiscountType.FIXED, 5000, null,
                IssueType.DOWNLOAD, 1000, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        given(getCouponUseCase.getById(1L))
                .willReturn(coupon);

        // when & then
        mockMvc.perform(get("/api/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트 쿠폰"));
    }

    @Test
    @DisplayName("쿠폰 목록을 조회할 수 있다")
    void getAllCoupons() throws Exception {
        // given
        List<Coupon> coupons = List.of(
                Coupon.of(1L, "쿠폰1", "설명1", CouponType.CART, DiscountType.FIXED, 5000, null,
                        IssueType.DOWNLOAD, 1000, 0,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30), 7, null, null),
                Coupon.of(2L, "쿠폰2", "설명2", CouponType.PRODUCT, DiscountType.RATE, 10, 3000,
                        IssueType.AUTO, 500, 0,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(15), 14, null, null)
        );

        Page<Coupon> couponPage = new PageImpl<>(coupons);

        given(getCouponUseCase.getAll(any(Pageable.class)))
                .willReturn(couponPage);

        // when & then
        mockMvc.perform(get("/api/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("쿠폰1"))
                .andExpect(jsonPath("$.content[1].name").value("쿠폰2"));
    }

    @Test
    @DisplayName("쿠폰을 수정할 수 있다")
    void updateCoupon() throws Exception {
        // given
        Coupon coupon = Coupon.of(
                1L, "수정된 쿠폰", "수정된 설명",
                CouponType.CART, DiscountType.RATE, 20, 10000,
                IssueType.DOWNLOAD, 500, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30),
                7, null, null
        );

        given(updateCouponUseCase.update(eq(1L), any(UpdateCouponCommand.class)))
                .willReturn(coupon);

        String requestJson = """
                {
                    "name": "수정된 쿠폰",
                    "description": "수정된 설명",
                    "couponType": "CART",
                    "discountType": "RATE",
                    "discountValue": 20,
                    "maxDiscount": 10000,
                    "issueType": "DOWNLOAD",
                    "totalQuantity": 500,
                    "issueStartAt": "2025-01-01T00:00:00",
                    "issueEndAt": "2025-01-31T23:59:59",
                    "validDays": 7
                }
                """;

        // when & then
        mockMvc.perform(put("/api/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("수정된 쿠폰"))
                .andExpect(jsonPath("$.discountValue").value(20));
    }

    @Test
    @DisplayName("쿠폰을 삭제할 수 있다")
    void deleteCoupon() throws Exception {
        // given
        doNothing().when(deleteCouponUseCase).delete(1L);

        // when & then
        mockMvc.perform(delete("/api/coupons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("쿠폰을 발급받을 수 있다")
    void issueCoupon() throws Exception {
        // given
        MemberCoupon memberCoupon = MemberCoupon.of(
                1L, 100L, 1L,
                MemberCouponStatus.AVAILABLE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                null
        );

        given(issueCouponUseCase.issue(eq(100L), eq(1L)))
                .willReturn(memberCoupon);

        // when & then
        mockMvc.perform(post("/api/coupons/1/issue")
                        .param("memberId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberId").value(100))
                .andExpect(jsonPath("$.couponId").value(1))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    @DisplayName("내 쿠폰 목록을 조회할 수 있다")
    void getMyCoupons() throws Exception {
        // given
        List<MemberCoupon> memberCoupons = List.of(
                MemberCoupon.of(1L, 100L, 1L,
                        MemberCouponStatus.AVAILABLE, LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7), null),
                MemberCoupon.of(2L, 100L, 2L,
                        MemberCouponStatus.USED, LocalDateTime.now(),
                        LocalDateTime.now().plusDays(14), LocalDateTime.now())
        );

        given(getMemberCouponUseCase.getByMemberId(100L))
                .willReturn(memberCoupons);

        // when & then
        mockMvc.perform(get("/api/coupons/my")
                        .param("memberId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].couponId").value(1))
                .andExpect(jsonPath("$[1].couponId").value(2));
    }

    @Test
    @DisplayName("사용 가능한 내 쿠폰 목록을 조회할 수 있다")
    void getMyAvailableCoupons() throws Exception {
        // given
        List<MemberCoupon> memberCoupons = List.of(
                MemberCoupon.of(1L, 100L, 1L,
                        MemberCouponStatus.AVAILABLE, LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7), null)
        );

        given(getMemberCouponUseCase.getByMemberIdAndStatus(100L, MemberCouponStatus.AVAILABLE))
                .willReturn(memberCoupons);

        // when & then
        mockMvc.perform(get("/api/coupons/my/available")
                        .param("memberId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }
}
