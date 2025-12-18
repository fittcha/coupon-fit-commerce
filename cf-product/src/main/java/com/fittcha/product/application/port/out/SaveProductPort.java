package com.fittcha.product.application.port.out;

import com.fittcha.product.domain.Product;

/*
Port Out = 내부(Service)가 외부(DB)를 호출하는 인터페이스

왜 인터페이스?
→ Service는 "저장해줘"만 요청
→ "어떻게 저장하는지"는 몰라도 됨 (JPA든 MyBatis든)
→ DB 바꿔도 Service 코드 안 바뀜!
*/
public interface SaveProductPort {

    Product save(Product product);
}
