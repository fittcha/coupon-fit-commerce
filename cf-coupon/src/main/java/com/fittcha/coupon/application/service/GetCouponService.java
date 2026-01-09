package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.GetCouponUseCase;
import com.fittcha.coupon.application.port.out.LoadCouponPort;
import com.fittcha.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCouponService implements GetCouponUseCase {

    private final LoadCouponPort loadCouponPort;

    @Override
    public Coupon getById(Long id) {
        return loadCouponPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id=" + id));
    }

    @Override
    public Page<Coupon> getAll(Pageable pageable) {
        return loadCouponPort.findAll(pageable);
    }
}
