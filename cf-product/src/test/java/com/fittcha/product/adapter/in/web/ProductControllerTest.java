package com.fittcha.product.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fittcha.product.application.port.in.GetProductUseCase;
import com.fittcha.product.application.port.in.RegisterProductCommand;
import com.fittcha.product.application.port.in.RegisterProductUseCase;
import com.fittcha.product.domain.Product;
import com.fittcha.product.domain.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void registerProduct() throws Exception {
        //given
        RegisterProductRequest request = new RegisterProductRequest();
        //request에 값 세팅이 필요하면 setter나 생성자 추가 필요

        Product product = Product.of(1L, 1L, 1L, "테스트 상품", "설명", 10000, ProductStatus.ON_SALE);

        given(registerProductUseCase.register(any(RegisterProductCommand.class)))
                .willReturn(product);

        //when&then
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
}
/*
@WebMvcTest → Controller만 테스트 (가벼움)
@MockBean → UseCase를 가짜로 대체
MockMvc → HTTP 요청 시뮬레이션
jsonPath → JSON 응답 검증
*/
