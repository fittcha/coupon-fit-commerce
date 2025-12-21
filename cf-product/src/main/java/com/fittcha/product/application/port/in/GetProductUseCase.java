package com.fittcha.product.application.port.in;

import com.fittcha.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetProductUseCase {

    Product getById(Long id);

    List<Product> getAll();

    Page<Product> getAll(Pageable pageable);
}
