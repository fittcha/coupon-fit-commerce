package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.UpdateCouponCommand;
import com.fittcha.coupon.application.port.in.UpdateCouponUseCase;
import com.fittcha.coupon.application.port.out.LoadCouponPort;
import com.fittcha.coupon.application.port.out.UpdateCouponPort;
import com.fittcha.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateCouponService implements UpdateCouponUseCase {

    private final LoadCouponPort loadCouponPort;
    private final UpdateCouponPort updateCouponPort;

    @Override
    public Coupon update(Long id, UpdateCouponCommand command) {
        Coupon coupon = loadCouponPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id=" + id));

        coupon.update(
                command.getName(),
                command.getDescription(),
                command.getCouponType(),
                command.getDiscountType(),
                command.getDiscountValue(),
                command.getMaxDiscount(),
                command.getIssueType(),
                command.getTotalQuantity(),
                command.getIssueStartAt(),
                command.getIssueEndAt(),
                command.getValidDays(),
                command.getValidUntil()
        );

        return updateCouponPort.update(coupon);
    }
}
