package com.fittcha.product.adapter.in.web;

import com.fittcha.product.application.port.in.GetProductUseCase;
import com.fittcha.product.application.port.in.RegisterProductUseCase;
import com.fittcha.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;
    private final GetProductUseCase getProductUseCase;

    @PostMapping
    public ResponseEntity<Product> register(@Valid @RequestBody RegisterProductRequest request) {
        Product product = registerProductUseCase.register(request.toCommand());
//        return ResponseEntity.created(URI.create("/api/products/" + product.getId()));
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = getProductUseCase.getById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Product> products = getProductUseCase.getAll(pageable);
        return ResponseEntity.ok(products);
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
