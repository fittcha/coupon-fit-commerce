package com.fittcha.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
1. @Getter만 사용 (@Setter 없음)
   → 외부에서 함부로 값 변경 못하게
   → 불변성 유지

2. 생성자 private + 정적 팩토리 메서드 (create)
   → 생성 로직을 한 곳에서 관리
   → status는 항상 ON_SALE로 시작

3. NoArgsConstructor(PROTECTED)
   → JPA용 기본 생성자 (나중에 엔티티 만들 때)
   → protected로 외부에서 빈 객체 생성 막음

4. @Builder(access = AccessLevel.PRIVATE)
   → 외부에서 builder() 직접 호출 막고, create() 메서드로만 생성하도록 강제
* */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    private Long id;
    private Long brandId;
    private Long categoryId;
    private String name;
    private String description;
    private int price;
    private ProductStatus status;

    public static Product create(Long brandId, Long categoryId, String name, String description, int price) {

        // 유효성 검증
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
        if (brandId == null) {
            throw new IllegalArgumentException("브랜드 Id는 필수입니다.");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("카테고리 id는 필수입니다.");
        }

        return Product.builder()
                .brandId(brandId)
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .price(price)
                .status(ProductStatus.ON_SALE)
                .build();
    }

    public static Product of(Long id, Long brandId, Long categoryId,
                             String name, String description, int price, ProductStatus status) {
        return Product.builder()
                .id(id)
                .brandId(brandId)
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .price(price)
                .status(status)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Product(Long id, Long brandId, Long categoryId, String name, String description, int price, ProductStatus status) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
}
