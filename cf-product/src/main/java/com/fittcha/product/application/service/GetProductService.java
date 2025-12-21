package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.GetProductUseCase;
import com.fittcha.product.application.port.out.LoadProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductService implements GetProductUseCase {

    private final LoadProductPort loadProductPort;

    @Override
    public Product getById(Long id) {
        return loadProductPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Override
    public List<Product> getAll() {
        return loadProductPort.findAll();
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return loadProductPort.findAll(pageable);
    }
}
