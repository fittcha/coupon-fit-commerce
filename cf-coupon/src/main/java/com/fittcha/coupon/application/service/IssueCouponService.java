package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.IssueCouponUseCase;
import com.fittcha.coupon.application.port.out.LoadCouponPort;
import com.fittcha.coupon.application.port.out.LoadMemberCouponPort;
import com.fittcha.coupon.application.port.out.SaveMemberCouponPort;
import com.fittcha.coupon.application.port.out.UpdateCouponPort;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueCouponService implements IssueCouponUseCase {

    private final LoadCouponPort loadCouponPort;
    private final UpdateCouponPort updateCouponPort;
    private final LoadMemberCouponPort loadMemberCouponPort;
    private final SaveMemberCouponPort saveMemberCouponPort;

    @Override
    public MemberCoupon issue(Long memberId, Long couponId) {
        // 이미 발급받은 쿠폰인지 확인
        if (loadMemberCouponPort.existsByMemberIdAndCouponId(memberId, couponId)) {
            throw new IllegalStateException("이미 발급받은 쿠폰입니다.");
        }

        // 쿠폰 조회
        Coupon coupon = loadCouponPort.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id=" + couponId));

        // 회원 쿠폰 발급 (도메인 로직에서 발급 가능 여부 검증)
        MemberCoupon memberCoupon = MemberCoupon.issue(memberId, coupon);

        // 쿠폰 발급 수량 업데이트
        updateCouponPort.update(coupon);

        return saveMemberCouponPort.save(memberCoupon);
    }
}
