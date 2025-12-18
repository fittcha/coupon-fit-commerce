package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.domain.ProductStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long brandId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    private Long createdBy;

    private Long updatedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private ProductJpaEntity(Long brandId, Long categoryId, String name, String description, int price, ProductStatus status,
                             Long createdBy, Long updatedBy) {
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
/*
### Hexagonal에서는 Domain과 Entity를 분리
왜 Domain과 Entity 분리?
```
Domain (Product)
→ 순수 비즈니스 로직
→ JPA 의존성 없음

Entity (ProductJpaEntity)
→ DB 매핑용
→ JPA 어노테이션 있음

분리하면:
→ Domain이 인프라(JPA)에 의존 안 함
→ DB 바꿔도 Domain 안 바뀜
→ Hexagonal 핵심!
*/
