package com.fittcha.product.adapter.in.web;

import com.fittcha.product.application.port.in.RegisterProductUseCase;
import com.fittcha.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;

    @PostMapping
    public ResponseEntity<Product> register(@Valid @RequestBody RegisterProductRequest request) {
        Product product = registerProductUseCase.register(request.toCommand());
//        return ResponseEntity.created(URI.create("/api/products/" + product.getId()));
        return ResponseEntity.ok(product);
    }
}
/*
@RestController: JSON 응답하는 Controller
@RequestMapping: 기본 URL 경로
@Valid: Request DTO 검증 실행
@RequestBody: JSON → 객체 변환

UseCase 인터페이스에 의존
→ 구현체(Service) 몰라도 됨
→ Hexagonal 핵심!
*/
