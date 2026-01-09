package com.fittcha.coupon.application.service;

import com.fittcha.coupon.application.port.in.DeleteCouponUseCase;
import com.fittcha.coupon.application.port.out.DeleteCouponPort;
import com.fittcha.coupon.application.port.out.LoadCouponPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteCouponService implements DeleteCouponUseCase {

    private final LoadCouponPort loadCouponPort;
    private final DeleteCouponPort deleteCouponPort;

    @Override
    public void delete(Long id) {
        loadCouponPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id=" + id));

        deleteCouponPort.deleteById(id);
    }
}
