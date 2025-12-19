package com.fittcha.product.adapter.in.web;

import com.fittcha.product.application.port.in.RegisterProductCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterProductRequest {

    @NotNull(message = "브랜드ID는 필수입니다")
    private Long brandId;

    @NotNull(message = "카테고리ID는 필수입니다")
    private Long categoryId;

    @NotBlank(message = "상품명은 필수입니다")
    private String name;

    private String description;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private int price;

    // Request -> Command 반환
    public RegisterProductCommand toCommand() {
        return RegisterProductCommand.builder()
                .brandId(brandId)
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
/*
Controller용 DTO (Request)와 Application용 DTO (Command) 분리

Request: HTTP 요청 데이터 + 검증 어노테이션
Command: UseCase에 전달하는 순수 데이터
*/
