package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.application.port.in.ProductSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fittcha.product.adapter.out.persistence.QProductJpaEntity.productJpaEntity;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductJpaEntity> search(ProductSearchCondition condition, Pageable pageable) {
        List<ProductJpaEntity> content = queryFactory
                .selectFrom(productJpaEntity)
                .where(
                        nameContains(condition.getName()),
                        brandIdEq(condition.getBrandId()),
                        categoryIdEq(condition.getCategoryId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(productJpaEntity.count())
                .from(productJpaEntity)
                .where(
                        nameContains(condition.getName()),
                        brandIdEq(condition.getBrandId()),
                        categoryIdEq(condition.getCategoryId())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression nameContains(String name) {
        return name != null && !name.isBlank() ? productJpaEntity.name.contains(name) : null;
    }

    private BooleanExpression brandIdEq(Long brandId) {
        return brandId != null ? productJpaEntity.brandId.eq(brandId) : null;
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? productJpaEntity.categoryId.eq(categoryId) : null;
    }
}
/*
BooleanExpression 메서드들
→ 조건이 null이면 null 반환
→ QueryDSL에서 null은 조건에서 무시됨
→ 동적 쿼리 완성!

nameContains("테스트") → WHERE name LIKE '%테스트%'
nameContains(null) → 조건 없음
*/
