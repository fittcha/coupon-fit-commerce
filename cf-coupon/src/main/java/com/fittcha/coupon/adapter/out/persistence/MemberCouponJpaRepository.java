package com.fittcha.coupon.adapter.out.persistence;

import com.fittcha.coupon.domain.MemberCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCouponJpaEntity, Long> {

    List<MemberCouponJpaEntity> findByMemberId(Long memberId);

    List<MemberCouponJpaEntity> findByMemberIdAndStatus(Long memberId, MemberCouponStatus status);

    boolean existsByMemberIdAndCouponId(Long memberId, Long couponId);
}
