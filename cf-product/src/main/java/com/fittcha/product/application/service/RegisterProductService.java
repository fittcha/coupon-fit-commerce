package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.RegisterProductCommand;
import com.fittcha.product.application.port.in.RegisterProductUseCase;
import com.fittcha.product.application.port.out.SaveProductPort;
import com.fittcha.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProductService implements RegisterProductUseCase {

    private final SaveProductPort saveProductPort;

    @Override
    public Product register(RegisterProductCommand command) {
        Product product = Product.create(
                command.getBrandId(),
                command.getCategoryId(),
                command.getName(),
                command.getDescription(),
                command.getPrice()
        );

        return saveProductPort.save(product);
    }
}
/*
implements RegisterProductUseCase
→ Port In 인터페이스 구현
→ Controller는 이 인터페이스만 알면 됨

SaveProductPort 주입
→ Port Out 인터페이스 사용
→ 실제 DB 구현체는 몰라도 됨

Product.create()
→ 도메인에서 검증 + 생성
→ Service는 흐름만 담당
*/
