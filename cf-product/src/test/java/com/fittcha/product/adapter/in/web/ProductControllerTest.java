package com.fittcha.product.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fittcha.product.application.port.in.*;
import com.fittcha.product.domain.Product;
import com.fittcha.product.domain.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterProductUseCase registerProductUseCase;

    @MockitoBean
    private GetProductUseCase getProductUseCase;

    @MockitoBean
    private UpdateProductUseCase updateProductUseCase;

    @MockitoBean
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void registerProduct() throws Exception {
        // given
        RegisterProductRequest request = new RegisterProductRequest();
        //request에 값 세팅이 필요하면 setter나 생성자 추가 필요

        Product product = Product.of(1L, 1L, 1L, "테스트 상품", "설명", 10000, ProductStatus.ON_SALE);

        given(registerProductUseCase.register(any(RegisterProductCommand.class)))
                .willReturn(product);

        // when & then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brandId\":1,\"categoryId\":1,\"name\":\"테스트 상품\",\"description\":\"설명\",\"price\":10000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트 상품"))
                .andExpect(jsonPath("$.price").value(10000));
    }

    @Test
    @DisplayName("상품을 조회할 수 있다")
    void getProduct() throws Exception {
        // given
        Product product = Product.of(1L, 1L, 1L, "테스트 상품", "설명", 10000, com.fittcha.product.domain.ProductStatus.ON_SALE);

        given(getProductUseCase.getById(1L))
                .willReturn(product);

        // when & then
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트 상품"));
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다")
    void getAllProducts() throws Exception {
        // given
        List<Product> products = List.of(
                Product.of(1L, 1L, 1L, "상품1", "설명1", 10000, com.fittcha.product.domain.ProductStatus.ON_SALE),
                Product.of(2L, 1L, 2L, "상품2", "설명2", 20000, com.fittcha.product.domain.ProductStatus.ON_SALE)
        );

        Page<Product> productPage = new PageImpl<>(products);

        given(getProductUseCase.getAll(any(Pageable.class))).willReturn(productPage);

        // when & then
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("상품1"))
                .andExpect(jsonPath("$.content[1].name").value("상품2"));
    }

    @Test
    @DisplayName("상품을 수정할 수 있다")
    void updateProduct() throws Exception {
        // given
        Product product = Product.of(1L, 1L, 1L, "수정된 상품", "설명", 20000, com.fittcha.product.domain.ProductStatus.ON_SALE);

        given(updateProductUseCase.update(any(Long.class), any()))
                .willReturn(product);

        Map<String, Object> request = Map.of(
                "brandId", 1,
                "categoryId", 1,
                "name", "수정된 상품",
                "description", "설명",
                "price", 20000
        );

        // when & then
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("수정된 상품"))
                .andExpect(jsonPath("$.price").value(20000));
    }

    @Test
    @DisplayName("상품을 삭제할 수 있다")
    void deleteProduct() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
/*
@WebMvcTest → Controller만 테스트 (가벼움)
@MockBean → UseCase를 가짜로 대체
MockMvc → HTTP 요청 시뮬레이션
jsonPath → JSON 응답 검증
*/
