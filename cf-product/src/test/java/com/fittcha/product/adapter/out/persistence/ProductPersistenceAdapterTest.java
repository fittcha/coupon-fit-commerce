package com.fittcha.product.adapter.out.persistence;

import com.fittcha.product.domain.Product;
import com.fittcha.product.domain.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ProductPersistenceAdapter.class, ProductMapper.class})
class ProductPersistenceAdapterTest {

    @Autowired
    private ProductPersistenceAdapter productPersistenceAdapter;

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void saveProduct() {
        //given
        Product product = Product.create(1L, 1L, "테스트 상품", "설명", 10000);

        //when
        Product savedProduct = productPersistenceAdapter.save(product);

        //then
        assertThat(savedProduct.getName()).isEqualTo("테스트 상품");
        assertThat(savedProduct.getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getStatus()).isEqualTo(ProductStatus.ON_SALE);
    }
}
/*
@DataJpaTest
→ JPA 관련 테스트용 (H2 자동 사용)

@Import({...})
→ 테스트에 필요한 클래스 등록
→ Adapter, Mapper는 자동 스캔 안 돼서 직접 등록
*/
