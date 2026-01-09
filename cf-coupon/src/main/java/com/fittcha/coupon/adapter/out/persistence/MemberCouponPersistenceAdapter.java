package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.application.port.out.LoadMemberCouponPort;
import com.fittcha.coupon.application.port.out.SaveMemberCouponPort;
import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCouponPersistenceAdapter implements SaveMemberCouponPort, LoadMemberCouponPort {

    private final MemberCouponJpaRepository memberCouponJpaRepository;
    private final MemberCouponMapper memberCouponMapper;

    @Override
    public MemberCoupon save(MemberCoupon memberCoupon) {
        MemberCouponJpaEntity entity = memberCouponMapper.toJpaEntity(memberCoupon);
        MemberCouponJpaEntity savedEntity = memberCouponJpaRepository.save(entity);
        return memberCouponMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MemberCoupon> findById(Long id) {
        return memberCouponJpaRepository.findById(id)
                .map(memberCouponMapper::toDomain);
    }

    @Override
    public List<MemberCoupon> findByMemberId(Long memberId) {
        return memberCouponJpaRepository.findByMemberId(memberId).stream()
                .map(memberCouponMapper::toDomain)
                .toList();
    }

    @Override
    public List<MemberCoupon> findByMemberIdAndStatus(Long memberId, MemberCouponStatus status) {
        return memberCouponJpaRepository.findByMemberIdAndStatus(memberId, status).stream()
                .map(memberCouponMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByMemberIdAndCouponId(Long memberId, Long couponId) {
        return memberCouponJpaRepository.existsByMemberIdAndCouponId(memberId, couponId);
    }
}
