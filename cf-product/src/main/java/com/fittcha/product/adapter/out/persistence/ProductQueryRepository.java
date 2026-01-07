package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.application.port.in.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {

    Page<ProductJpaEntity> search(ProductSearchCondition condition, Pageable pageable);
}
