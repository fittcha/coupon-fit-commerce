package com.fittcha.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestConfig {

    /*
    22:34:15.223 [Test worker] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.fittcha.product.adapter.out.persistence.ProductPersistenceAdapterTest]: ProductPersistenceAdapterTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.

    Unable to find a @SpringBootConfiguration by searching packages upwards from the test. You can use @ContextConfiguration, @SpringBootTest(classes=...) or other Spring Test supported mechanisms to explicitly declare the configuration classes to load. Classes annotated with @TestConfiguration are not considered.
    java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration by searching packages upwards from the test. You can use @ContextConfiguration, @SpringBootTest(classes=...) or other Spring Test supported mechanisms to explicitly declare the configuration classes to load. Classes annotated with @TestConfiguration are not considered.
	at org.springframework.util.Assert.state(Assert.java:79)

	-> 멀티 모듈이라서 생기는 문제
	    @DataJpaTest가 @SpringBootApplication을 찾는데
        → cf-product 모듈에는 없음
        → cf-app 모듈에 있음
        → "못 찾겠어!" 에러

    왜?
        @SpringBootApplication이 있어야
        → @DataJpaTest가 Spring 설정을 찾을 수 있음
        → 테스트 전용으로 만들어주는 것
	*/
}
