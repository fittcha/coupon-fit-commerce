package com.fittcha.product.application.service;

import com.fittcha.product.application.port.in.DeleteProductUseCase;
import com.fittcha.product.application.port.out.DeleteProductPort;
import com.fittcha.product.application.port.out.LoadProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteProductService implements DeleteProductUseCase {

    private final LoadProductPort loadProductPort;
    private final DeleteProductPort deleteProductPort;

    @Override
    public void delete(Long id) {
        loadProductPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));

        deleteProductPort.delete(id);
    }
}
/*
조회 + 삭제가 하나의 트랜잭션이어야 함

없으면?
→ 조회 성공
→ 삭제 전에 다른 요청이 먼저 삭제
→ 데이터 정합성 문제 가능

필수는 아니지만, 안전하게 붙이는 습관!
여러 작업이 하나의 단위로 묶여야 할 때 사용
*/
