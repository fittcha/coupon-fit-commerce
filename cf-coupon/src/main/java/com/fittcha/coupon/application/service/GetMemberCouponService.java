package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.GetMemberCouponUseCase;
import com.fittcha.coupon.application.port.out.LoadMemberCouponPort;
import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberCouponService implements GetMemberCouponUseCase {

    private final LoadMemberCouponPort loadMemberCouponPort;

    @Override
    public List<MemberCoupon> getByMemberId(Long memberId) {
        return loadMemberCouponPort.findByMemberId(memberId);
    }

    @Override
    public List<MemberCoupon> getByMemberIdAndStatus(Long memberId, MemberCouponStatus status) {
        return loadMemberCouponPort.findByMemberIdAndStatus(memberId, status);
    }
}
