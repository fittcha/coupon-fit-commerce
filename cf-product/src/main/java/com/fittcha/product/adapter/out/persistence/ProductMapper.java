package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Domain -> JPA Entity
    public ProductJpaEntity toJpaEntity(Product product) {
        return ProductJpaEntity.builder()
                .brandId(product.getBrandId())
                .categoryId(product.getCategoryId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

    // JPA Entity -> Domain
    public Product toDomain(ProductJpaEntity entity) {
        return Product.of(
                entity.getId(),
                entity.getBrandId(),
                entity.getCategoryId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStatus()
        );
    }
}
/*
Domain과 Entity가 분리되어 있으니까
→ 서로 변환해주는 애가 필요

toEntity(): 저장할 때 (Domain → Entity)
toDomain(): 조회할 때 (Entity → Domain)
*/
