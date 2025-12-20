package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.GetProductUseCase;
import com.fittcha.product.application.port.out.LoadProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductService implements GetProductUseCase {

    private final LoadProductPort loadProductPort;

    @Override
    public Product getById(Long id) {
        return loadProductPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
