package com.fittcha.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void createProduct() {
        //given
        Long brandId = 1L;
        Long categoryId = 1L;
        String name = "테스트 상품";
        String description = "상품 설명입니다.";
        int price = 10000;

        //when
        Product product = Product.create(brandId, categoryId, name, description, price);

        //then
        assertThat(product.getBrandId()).isEqualTo(brandId);
        assertThat(product.getCategoryId()).isEqualTo(categoryId);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.ON_SALE);
    }
}
