package com.fittcha.product.application.port.in;

import com.fittcha.product.domain.Product;

/*
Port In = 외부(Controller)가 내부(Service)를 호출하는 인터페이스

인터페이스로 만드는 이유:
→ Controller는 "뭘 할 수 있는지"만 앎
→ "어떻게 하는지"는 몰라도 됨 (구현체가 처리)
→ 느슨한 결합 (테스트하기 쉬움)
* */
public interface RegisterProductUseCase {

    Product register(RegisterProductCommand command);
}
