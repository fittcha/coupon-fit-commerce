package com.fittcha.product.application.port.in;

import com.fittcha.product.domain.Product;

public interface UpdateProductUseCase {

    Product update(Long id, UpdateProductCommand command);
}
