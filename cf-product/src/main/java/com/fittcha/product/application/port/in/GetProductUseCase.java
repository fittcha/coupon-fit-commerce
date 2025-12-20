package com.fittcha.product.application.port.in;

import com.fittcha.product.domain.Product;

public interface GetProductUseCase {

    Product getById(Long id);
}
