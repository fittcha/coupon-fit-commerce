package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.application.port.out.DeleteCouponPort;
import com.fittcha.coupon.application.port.out.LoadCouponPort;
import com.fittcha.coupon.application.port.out.SaveCouponPort;
import com.fittcha.coupon.application.port.out.UpdateCouponPort;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.CouponCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponPersistenceAdapter implements SaveCouponPort, LoadCouponPort, UpdateCouponPort, DeleteCouponPort {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponMapper couponMapper;

    @Override
    public Coupon save(Coupon coupon) {
        CouponJpaEntity entity = couponMapper.toJpaEntity(coupon);

        // 조건 추가
        for (CouponCondition condition : coupon.getConditions()) {
            CouponConditionJpaEntity conditionEntity = couponMapper.toConditionJpaEntity(condition);
            entity.addCondition(conditionEntity);
        }

        CouponJpaEntity savedEntity = couponJpaRepository.save(entity);
        return couponMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return couponJpaRepository.findById(id)
                .map(couponMapper::toDomain);
    }

    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return couponJpaRepository.findAll(pageable)
                .map(couponMapper::toDomain);
    }

    @Override
    public Coupon update(Coupon coupon) {
        CouponJpaEntity entity = couponJpaRepository.findById(coupon.getId())
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id=" + coupon.getId()));

        entity.update(
                coupon.getName(),
                coupon.getDescription(),
                coupon.getCouponType(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getMaxDiscount(),
                coupon.getIssueType(),
                coupon.getTotalQuantity(),
                coupon.getIssueStartAt(),
                coupon.getIssueEndAt(),
                coupon.getValidDays(),
                coupon.getValidUntil()
        );

        // 발급 수량이 증가한 경우 반영
        while (entity.getIssuedQuantity() < coupon.getIssuedQuantity()) {
            entity.incrementIssuedQuantity();
        }

        return couponMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        couponJpaRepository.deleteById(id);
    }
}
