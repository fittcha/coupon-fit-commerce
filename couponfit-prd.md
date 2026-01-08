# CouponFit Commerce - PRD

## 1. 프로젝트 개요

### 목적
- 쿠폰 최적화에 특화된 이커머스 플랫폼
- 복합 조건 쿠폰(브랜드, 등급, 최소/최대 주문금액) 지원
- 모듈러 모놀리스 + Hexagonal 아키텍처 학습 및 포트폴리오

### 타겟 사용자
- 소비자: 상품 구매, 쿠폰 적용, 최적 할인 추천 받기
- 셀러/관리자: 상품 관리, 쿠폰 발급, 주문 관리

---

## 2. 기술 스택

### Backend
- Java 25, Spring Boot 3.5.8
- Spring Data JPA, QueryDSL
- Spring Security (OAuth2 소셜 로그인)

### Database
- MySQL (운영)
- H2 (테스트)

### Architecture
- 모듈러 모놀리스
- Hexagonal Architecture (Port & Adapter)
- TDD (JUnit5, AssertJ, Mockito)

### 모듈 구조
```
coupon-fit/
├── cf-app/        # 메인 실행 모듈
├── cf-common/     # 공통 모듈
├── cf-product/    # 상품 모듈 ✅ 완료
├── cf-member/     # 회원 모듈
├── cf-coupon/     # 쿠폰 모듈
├── cf-cart/       # 장바구니 모듈
├── cf-order/      # 주문 모듈
└── cf-claim/      # 클레임 모듈
```

---

## 3. 도메인 및 기능 목록

### 3.1 회원 (Member)
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 소셜 로그인 | 카카오, 네이버, 구글 | P1 |
| 회원 등급 | ROOKIE → FAMILY → VIP 자동 승급 | P1 |
| 프로필 관리 | 이름, 연락처, 배송지 | P2 |

### 3.2 상품 (Product) ✅ 완료
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 상품 CRUD | 등록, 조회, 수정, 삭제 | P1 ✅ |
| 상품 검색 | 이름, 브랜드, 카테고리 | P1 ✅ |
| 페이징 | 목록 페이징 처리 | P1 ✅ |
| 상품 옵션 | 사이즈, 색상 등 | P2 |
| 재고 관리 | 옵션별 재고 | P2 |

### 3.3 쿠폰 (Coupon)
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 쿠폰 발급 | 정액/정률 할인 | P1 |
| 복합 조건 | 브랜드, 등급, 최소/최대 주문금액 | P1 |
| 쿠폰 적용 | 장바구니에서 최적 쿠폰 추천 | P1 |
| 쿠폰 이력 | 발급/사용 이력 관리 | P2 |

### 3.4 장바구니 (Cart)
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 장바구니 담기 | 상품 추가/수량 변경/삭제 | P1 |
| 쿠폰 미리보기 | 적용 가능 쿠폰 및 할인액 표시 | P1 |

### 3.5 주문 (Order)
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 주문 생성 | 장바구니 → 주문 | P1 |
| 결제 연동 | PG사 연동 (토스페이먼츠) | P2 |
| 주문 상태 | 결제완료 → 배송중 → 배송완료 | P1 |
| 주문 이력 | 주문 내역 조회 | P1 |

### 3.6 클레임 (Claim)
| 기능 | 설명 | 우선순위 |
|------|------|----------|
| 취소 | 주문 취소 | P2 |
| 반품 | 반품 요청/처리 | P2 |
| 환불 | 환불 처리 | P2 |

---

## 4. ERD 요약

### 테이블 (17개)
- 회원: member, member_grade_history
- 상품: brand, category, product, product_option, product_option_stock
- 쿠폰: coupon, coupon_condition, member_coupon
- 장바구니: cart, cart_item
- 주문: orders, order_item, payment, order_status_history
- 클레임: claim, claim_item, claim_payment

### 회원 등급
- ROOKIE: 신규 가입
- FAMILY: 누적 구매 10만원 이상
- VIP: 누적 구매 50만원 이상

### 쿠폰 조건 타입
- BRAND: 특정 브랜드만
- GRADE: 특정 등급 이상
- MIN_ORDER: 최소 주문금액
- MAX_ORDER: 최대 주문금액

---

## 5. API 설계

### Product API ✅ 완료
```
POST   /api/products              # 상품 등록
GET    /api/products/{id}         # 상품 조회
GET    /api/products              # 상품 목록/검색 (페이징)
PUT    /api/products/{id}         # 상품 수정
DELETE /api/products/{id}         # 상품 삭제
```

### Member API (예정)
```
POST   /api/auth/login            # 소셜 로그인
GET    /api/members/me            # 내 정보 조회
PUT    /api/members/me            # 내 정보 수정
GET    /api/members/me/grade      # 내 등급 조회
```

### Coupon API (예정)
```
POST   /api/coupons               # 쿠폰 생성 (관리자)
GET    /api/coupons               # 쿠폰 목록
POST   /api/coupons/{id}/issue    # 쿠폰 발급
GET    /api/members/me/coupons    # 내 쿠폰 목록
```

### Cart API (예정)
```
GET    /api/cart                  # 장바구니 조회
POST   /api/cart/items            # 장바구니 담기
PUT    /api/cart/items/{id}       # 수량 변경
DELETE /api/cart/items/{id}       # 장바구니 삭제
GET    /api/cart/coupons          # 적용 가능 쿠폰 목록
```

### Order API (예정)
```
POST   /api/orders                # 주문 생성
GET    /api/orders                # 주문 목록
GET    /api/orders/{id}           # 주문 상세
POST   /api/orders/{id}/cancel    # 주문 취소
```

---

## 6. 개발 우선순위

### Phase 1 (MVP) - 현재
1. ✅ 상품 도메인 완료
2. 회원 도메인 (소셜 로그인, 등급)
3. 쿠폰 도메인 (발급, 복합 조건)

### Phase 2
4. 장바구니 도메인
5. 주문 도메인
6. 쿠폰 최적화 추천 기능

### Phase 3
7. 클레임 도메인
8. 결제 연동
9. AI 기능 (상품/쿠폰 추천)

---

## 7. 제약사항 및 고려사항

### 성능
- 대용량 쿠폰 발급 처리 가능
- 상품 검색 QueryDSL 최적화

### 보안
- Spring Security + OAuth2
- API 인증/인가

### 테스트
- 단위 테스트 (Domain, Service)
- 통합 테스트 (Repository)
- API 테스트 (Controller)

---

## 8. 향후 확장 (AI 활용)

### 대고객
- AI 상품 추천
- AI 쿠폰 추천
- AI 챗봇 상담

### 셀러/관리자
- AI CRM (고객 세그먼트 자동 분류)
- AI 마케팅 (타겟 고객 추천, 캠페인 자동 생성)
- AI 이탈 예측 (재구매 유도 타이밍 추천)
- AI 리뷰 분석 (상품 개선점 도출)
- AI 매출 예측 (재고 관리 연동)

---

## 9. 현재 진행 상황

### 완료
- [x] 프로젝트 초기 설정 (Spring Boot 3.5.8, Java 25)
- [x] 멀티 모듈 구조 (cf-app, cf-common, cf-product)
- [x] Hexagonal 아키텍처 패키지 구조
- [x] Product 도메인 전체 (CRUD, 검색, 페이징)
- [x] QueryDSL 설정
- [x] 테스트 코드 (Domain, Service, Controller)

### 진행 예정
- [ ] 예외 처리 고도화 (GlobalExceptionHandler)
- [ ] Member 도메인
- [ ] Coupon 도메인
