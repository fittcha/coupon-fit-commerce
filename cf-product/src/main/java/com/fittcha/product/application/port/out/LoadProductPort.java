package com.fittcha.product.application.port.out;

import com.fittcha.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface LoadProductPort {

    Optional<Product> findById(Long id);

    List<Product> findAll();
}
