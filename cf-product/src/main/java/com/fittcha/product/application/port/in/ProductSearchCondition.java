package com.fittcha.product.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSearchCondition {

    private final String name;
    private final Long brandId;
    private final Long categoryId;
}
/*
검색 조건을 객체로 묶으면
→ 파라미터 관리 쉬움
→ 조건 추가해도 메서드 시그니처 안 바뀜
*/
