package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.application.port.out.SaveProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements SaveProductPort {

    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = productMapper.toJpaEntity(product);
        ProductJpaEntity saveEntity = productJpaRepository.save(entity);
        return productMapper.toDomain(saveEntity);
    }
}
/*
implements SaveProductPort
→ Port Out 인터페이스 구현
→ Service는 이 인터페이스만 알면 됨

흐름:
1. Domain → Entity 변환 (toEntity)
2. DB 저장 (JpaRepository.save)
3. Entity → Domain 변환 (toDomain)
4. 리턴

Service는 JPA 몰라도 됨!
→ Hexagonal 핵심: 도메인이 인프라에 의존 안 함
*/
