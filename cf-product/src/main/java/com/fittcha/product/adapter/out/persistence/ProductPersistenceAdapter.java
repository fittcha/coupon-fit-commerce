package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.application.port.out.DeleteProductPort;
import com.fittcha.product.application.port.out.LoadProductPort;
import com.fittcha.product.application.port.out.SaveProductPort;
import com.fittcha.product.application.port.out.UpdateProductPort;
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
public class ProductPersistenceAdapter implements SaveProductPort, LoadProductPort, UpdateProductPort, DeleteProductPort {

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

    @Override
    public void delete(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        ProductJpaEntity entity = productJpaRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + product.getId()));

        entity.update(
                product.getBrandId(),
                product.getCategoryId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );

        return productMapper.toDomain(entity);
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

update()가 왜 이렇게?
JPA 더티체킹 활용
→ 엔티티 조회 후 값 변경하면
→ 트랜잭션 끝날 때 자동 UPDATE
    1. findById()로 엔티티 조회 → 영속성 컨텍스트에 저장됨
    2. entity.update()로 값 변경
    3. 트랜잭션 끝날 때 JPA가 자동으로 변경 감지
    4. UPDATE 쿼리 자동 실행!
주의
    더티체킹은 @Transactional 안에서만 동작
    Service에 @Transactional 추가 필요!
*/
