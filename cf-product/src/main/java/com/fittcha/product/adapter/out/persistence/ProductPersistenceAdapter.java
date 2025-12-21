package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.application.port.out.LoadProductPort;
import com.fittcha.product.application.port.out.SaveProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements SaveProductPort, LoadProductPort {

    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper productMapper;

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = productMapper.toJpaEntity(product);
        ProductJpaEntity saveEntity = productJpaRepository.save(entity);
        return productMapper.toDomain(saveEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable)
                .map(productMapper::toDomain);
        /*
        왜 .stream() 없어?
            Page는 이미 .map() 지원함
            → 내부 요소들 변환하고 Page로 반환
        */
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
