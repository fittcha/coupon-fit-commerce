package com.fittcha.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    /*
     * 상품 등록 케이스 검증
     */
    @Test
    @DisplayName("상품명이 없으면 예외가 발생한다.")
    void createProductWithoutName() {
        //given
        Long brandId = 1L;
        Long categoryId = 1L;
        String name = null;
        String description = "상품 설명입니다.";
        int price = 10000;

        //when&then
        assertThatThrownBy(() -> Product.create(brandId, categoryId, name, description, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 필수입니다.");
    }

    @Test
    @DisplayName("가격이 0원 미만이면 예외가 발생한다.")
    void createProductWithNegativePrice() {
        //given
        Long brandId = 1L;
        Long categoryId = 1L;
        String name = "테스트 상품";
        String description = "상품 설명입니다.";
        int price = -1000;

        //when&then
        assertThatThrownBy(() -> Product.create(brandId, categoryId, name, description, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("브랜드 id가 없으면 예외가 발생한다.")
    void createProductWithoutBrandId() {
        //given
        Long brandId = null;
        Long categoryId = 1L;
        String name = "테스트 상품";
        String description = "상품 설명입니다.";
        int price = 10000;

        //when&then
        assertThatThrownBy(() -> Product.create(brandId, categoryId, name, description, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("브랜드 Id는 필수입니다.");
    }

    @Test
    @DisplayName("카테고리 Id가 없으이면 예외가 발생한다.")
    void createProductWithoutCategoryId() {
        //given
        Long brandId = 1L;
        Long categoryId = null;
        String name = "테스트 상품";
        String description = "상품 설명입니다.";
        int price = 10000;

        //when&then
        assertThatThrownBy(() -> Product.create(brandId, categoryId, name, description, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 id는 필수입니다.");
    }
}
