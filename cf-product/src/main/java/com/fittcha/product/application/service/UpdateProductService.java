package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.UpdateProductCommand;
import com.fittcha.product.application.port.in.UpdateProductUseCase;
import com.fittcha.product.application.port.out.LoadProductPort;
import com.fittcha.product.application.port.out.UpdateProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProductService implements UpdateProductUseCase {

    private final LoadProductPort loadProductPort;
    private final UpdateProductPort updateProductPort;

    @Override
    public Product update(Long id, UpdateProductCommand command) {
        Product product = loadProductPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id: " + id));

        product.update(
                command.getBrandId(),
                command.getCategoryId(),
                command.getName(),
                command.getDescription(),
                command.getPrice()
        );

        return updateProductPort.update(product);
    }
}
/*
@Transactional
→ 더티체킹이 동작하려면 필요
→ 메서드 끝날 때 자동 commit
→ 에러 나면 자동 rollback
*/
