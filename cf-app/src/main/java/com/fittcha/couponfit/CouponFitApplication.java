package com.fittcha.couponfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.fittcha")
@EntityScan(basePackages = "com.fittcha")
@EnableJpaRepositories(basePackages = "com.fittcha")
public class CouponFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponFitApplication.class, args);
	}

}
/*
scanBasePackages = "com.fittcha"
→ com.fittcha 하위 전부 스캔
→ com.fittcha.couponfit (SecurityConfig)
→ com.fittcha.product (Controller, Service, Repository)

*런타임 JpaRepository 못 찾는 에러
@SpringBootApplication(scanBasePackages = "com.fittcha")
→ 컴포넌트(@Controller, @Service 등)는 스캔됨
→ 근데 JPA Repository는 별도 설정 필요!

@EntityScan: JPA Entity 스캔 범위
@EnableJpaRepositories: JPA Repository 스캔 범위
이렇게 많이 선언해야 해?
*/
