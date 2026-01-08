package com.fittcha.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * QueryDSL 테스트용 설정
 * 
 * @DataJpaTest에서 JPAQueryFactory가 자동 등록되지 않아 직접 등록
 */
@TestConfiguration
public class QueryDslTestConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
