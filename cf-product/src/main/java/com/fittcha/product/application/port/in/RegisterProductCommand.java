package com.fittcha.product.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterProductCommand {

    private final Long brandId;
    private final Long categoryId;
    private final String name;
    private final String description;
    private final int price;
}
/*
* Command Pattern
*
* Command 객체는 단순한 데이터 운반체
* UseCase에 전달하는 데이터를 담는 객체
→ 비즈니스 로직 없음
→ 검증은 Domain(Product)에서 함

왜 따로 만들어?
→ Controller의 DTO와 분리
→ UseCase가 필요한 데이터만 명확하게 정의
→ final로 불변 보장

Command는 그냥 택배 상자
→ 데이터 담아서 전달만
→ 복잡한 보호 장치 필요 없음
→ @Getter @Builder면 충분!
* */
