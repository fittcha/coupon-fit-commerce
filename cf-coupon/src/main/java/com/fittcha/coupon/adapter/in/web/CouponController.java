package com.fittcha.coupon.adapter.in.web;

import com.fittcha.coupon.application.port.in.*;
import com.fittcha.coupon.domain.Coupon;
import com.fittcha.coupon.domain.MemberCoupon;
import com.fittcha.coupon.domain.MemberCouponStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CreateCouponUseCase createCouponUseCase;
    private final GetCouponUseCase getCouponUseCase;
    private final UpdateCouponUseCase updateCouponUseCase;
    private final DeleteCouponUseCase deleteCouponUseCase;
    private final IssueCouponUseCase issueCouponUseCase;
    private final GetMemberCouponUseCase getMemberCouponUseCase;

    // 쿠폰 생성 (관리자)
    @PostMapping
    public ResponseEntity<Coupon> create(@Valid @RequestBody CreateCouponRequest request) {
        Coupon coupon = createCouponUseCase.create(request.toCommand());
        return ResponseEntity.ok(coupon);
    }

    // 쿠폰 목록 조회 (관리자)
    @GetMapping
    public ResponseEntity<Page<Coupon>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Coupon> coupons = getCouponUseCase.getAll(pageable);
        return ResponseEntity.ok(coupons);
    }

    // 쿠폰 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getById(@PathVariable Long id) {
        Coupon coupon = getCouponUseCase.getById(id);
        return ResponseEntity.ok(coupon);
    }

    // 쿠폰 수정 (관리자)
    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(@PathVariable Long id, @Valid @RequestBody UpdateCouponRequest request) {
        Coupon coupon = updateCouponUseCase.update(id, request.toCommand());
        return ResponseEntity.ok(coupon);
    }

    // 쿠폰 삭제 (관리자)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCouponUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 쿠폰 다운로드 (회원)
    @PostMapping("/{id}/issue")
    public ResponseEntity<MemberCoupon> issue(@PathVariable Long id, @RequestParam Long memberId) {
        // TODO: 실제로는 인증된 회원 정보에서 memberId를 가져와야 함
        MemberCoupon memberCoupon = issueCouponUseCase.issue(memberId, id);
        return ResponseEntity.ok(memberCoupon);
    }

    // 내 쿠폰 목록 (회원)
    @GetMapping("/my")
    public ResponseEntity<List<MemberCoupon>> getMyCoupons(@RequestParam Long memberId) {
        // TODO: 실제로는 인증된 회원 정보에서 memberId를 가져와야 함
        List<MemberCoupon> coupons = getMemberCouponUseCase.getByMemberId(memberId);
        return ResponseEntity.ok(coupons);
    }

    // 사용 가능한 내 쿠폰 목록 (회원)
    @GetMapping("/my/available")
    public ResponseEntity<List<MemberCoupon>> getMyAvailableCoupons(@RequestParam Long memberId) {
        // TODO: 실제로는 인증된 회원 정보에서 memberId를 가져와야 함
        List<MemberCoupon> coupons = getMemberCouponUseCase.getByMemberIdAndStatus(memberId, MemberCouponStatus.AVAILABLE);
        return ResponseEntity.ok(coupons);
    }
}
