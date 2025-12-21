package com.fittcha.product.application.port.in;

import com.fittcha.product.domain.Product;

import java.util.List;

public interface GetProductUseCase {

    Product getById(Long id);

    List<Product> getAll();
}
