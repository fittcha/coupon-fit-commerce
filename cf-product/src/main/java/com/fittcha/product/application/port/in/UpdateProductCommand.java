package com.fittcha.product.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProductCommand {

    private final Long brandId;
    private final Long categoryId;
    private final String name;
    private final String description;
    private final int price;
}
