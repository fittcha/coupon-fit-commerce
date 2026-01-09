package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.CreateCouponCommand;
import com.fittcha.coupon.application.port.in.CreateCouponUseCase;
import com.fittcha.coupon.application.port.out.SaveCouponPort;
import com.fittcha.coupon.domain.ConditionType;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.CouponCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCouponService implements CreateCouponUseCase {

    private final SaveCouponPort saveCouponPort;

    @Override
    public Coupon create(CreateCouponCommand command) {
        Coupon coupon = Coupon.create(
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

        Coupon savedCoupon = saveCouponPort.save(coupon);

        if (command.getConditions() != null) {
            for (CreateCouponCommand.ConditionCommand conditionCommand : command.getConditions()) {
                CouponCondition condition = CouponCondition.create(
                        savedCoupon.getId(),
                        ConditionType.valueOf(conditionCommand.getConditionType()),
                        conditionCommand.getConditionValue()
                );
                savedCoupon.addCondition(condition);
            }
        }

        return savedCoupon;
    }
}
