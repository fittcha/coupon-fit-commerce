# CouponFit Commerce 작업 내역

## ✅ 완료된 작업 (Completed)

### 1. 프로젝트 초기 설정 및 문서화
- [x] 멀티 모듈 프로젝트 구조 설정 (`cf-app`, `cf-common`, `cf-product`, `cf-member`)
- [x] 아키텍처 문서화 (`docs/ARCHITECTURE.md`)
    - Hexagonal Architecture 구조 정의
    - API 명세 및 개발 컨벤션 수립
- [x] 데이터베이스 설계 (`docs/DATABASE_DESIGN.md`)

### 2. Member 도메인 구현
- [x] `cf-member` 모듈 생성 및 의존성 설정
- [x] 도메인 레이어 구현 (Member, Enums)
- [x] 애플리케이션 레이어 구현 (UseCase, Port, Service)
- [x] 어댑터 레이어 구현 (Web Controller, JPA Persistence)
- [x] 테스트 코드 작성 (Domain, Service, Controller)

### 3. 유지보수 및 개선
- [x] `cf-product` 테스트 환경 개선
    - TestConfig 의존성 충돌 해결 (Slice Test 지원)
    - QueryDSL 설정 분리

---

## 🚀 예정된 작업 (Backlog)

### Phase 2: 인증/인가 고도화
- [ ] Spring Security 및 OAuth2 (Kakao, Naver, Google) 연동
- [ ] JWT 발급 및 인증 필터 구현

### Phase 3: 도메인 연동 및 확장
- [ ] 상품(Product) - 회원(Member) 연동 (관심상품, 장바구니 등)
- [ ] 쿠폰(Coupon) 도메인 구현
