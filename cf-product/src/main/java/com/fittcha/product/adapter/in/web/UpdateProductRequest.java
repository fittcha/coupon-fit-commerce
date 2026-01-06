package com.fittcha.product.adapter.in.web;

import com.fittcha.product.application.port.in.UpdateProductCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductRequest {

    @NotNull(message = "브랜드ID는 필수입니다")
    private Long brandId;

    @NotNull(message = "카테고리ID는 필수입니다")
    private Long categoryId;

    @NotBlank(message = "상품명은 필수입니다")
    private String name;

    private String description;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private int price;

    public UpdateProductCommand toCommand() {
        return UpdateProductCommand.builder()
                .brandId(brandId)
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
