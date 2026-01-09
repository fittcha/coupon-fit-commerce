package com.fittcha.coupon;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 테스트용 Spring Boot Application 설정
 *
 * 멀티 모듈이라서 @DataJpaTest, @WebMvcTest가 @SpringBootApplication을 찾는데
 * cf-coupon 모듈에는 없음 (cf-app 모듈에 있음)
 * 테스트 전용으로 만들어주는 것
 */
@SpringBootApplication
public class TestConfig {
}
