package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.CreateCouponCommand;
import com.fittcha.coupon.application.port.out.SaveCouponPort;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.CouponType;
import com.fittcha.coupon.domain.DiscountType;
import com.fittcha.coupon.domain.IssueType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateCouponServiceTest {

    @InjectMocks
    private CreateCouponService createCouponService;

    @Mock
    private SaveCouponPort saveCouponPort;

    @Test
    @DisplayName("쿠폰을 생성할 수 있다")
    void createCoupon() {
        // given
        CreateCouponCommand command = CreateCouponCommand.builder()
                .name("신규 회원 10% 할인")
                .description("신규 가입 회원 전용 쿠폰")
                .couponType(CouponType.CART)
                .discountType(DiscountType.RATE)
                .discountValue(10)
                .maxDiscount(5000)
                .issueType(IssueType.DOWNLOAD)
                .totalQuantity(1000)
                .issueStartAt(LocalDateTime.now())
                .issueEndAt(LocalDateTime.now().plusDays(30))
                .validDays(7)
                .build();

        given(saveCouponPort.save(any(Coupon.class)))
                .willAnswer(invocation -> {
                    Coupon coupon = invocation.getArgument(0);
                    return Coupon.of(
                            1L,
                            coupon.getName(),
                            coupon.getDescription(),
                            coupon.getCouponType(),
                            coupon.getDiscountType(),
                            coupon.getDiscountValue(),
                            coupon.getMaxDiscount(),
                            coupon.getIssueType(),
                            coupon.getTotalQuantity(),
                            coupon.getIssuedQuantity(),
                            coupon.getIssueStartAt(),
                            coupon.getIssueEndAt(),
                            coupon.getValidDays(),
                            coupon.getValidUntil(),
                            coupon.getConditions()
                    );
                });

        // when
        Coupon result = createCouponService.create(command);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("신규 회원 10% 할인");
        assertThat(result.getCouponType()).isEqualTo(CouponType.CART);
        assertThat(result.getDiscountType()).isEqualTo(DiscountType.RATE);
        assertThat(result.getDiscountValue()).isEqualTo(10);
        assertThat(result.getIssuedQuantity()).isEqualTo(0);
        verify(saveCouponPort).save(any(Coupon.class));
    }
}
