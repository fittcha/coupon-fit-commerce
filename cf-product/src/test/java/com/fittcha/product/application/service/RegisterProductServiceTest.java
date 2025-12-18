package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.RegisterProductCommand;
import com.fittcha.product.application.port.out.SaveProductPort;
import com.fittcha.product.domain.Product;
import com.fittcha.product.domain.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegisterProductServiceTest {

    @InjectMocks
    private RegisterProductService registerProductService;

    @Mock
    private SaveProductPort saveProductPort;

    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void registerProduct() {
        //given
        RegisterProductCommand command = RegisterProductCommand.builder()
                .brandId(1L)
                .categoryId(1L)
                .name("테스트 상품")
                .description("상품 설명입니다")
                .price(10000)
                .build();

        given(saveProductPort.save(any(Product.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        Product result = registerProductService.register(command);

        //then
        assertThat(result.getName()).isEqualTo("테스트 상품");
        assertThat(result.getPrice()).isEqualTo(10000);
        assertThat(result.getStatus()).isEqualTo(ProductStatus.ON_SALE);
        verify(saveProductPort).save(any(Product.class));
    }
}
/*
@ExtendWith(MockitoExtension.class)
→ Mockito 사용 설정

@Mock SaveProductPort
→ 가짜 DB (실제 DB 없이 테스트)

@InjectMocks RegisterProductService
→ Mock을 주입받은 Service

given(...).willAnswer(...)
→ save 호출하면 받은 그대로 리턴하게 설정

verify(...)
→ save가 호출됐는지 확인
*/
