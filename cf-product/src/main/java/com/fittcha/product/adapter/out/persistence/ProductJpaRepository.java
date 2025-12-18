package com.fittcha.product.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
/*
JpaRepository 상속하면
→ save(), findById(), findAll(), delete() 등 자동 제공
→ 구현 안 해도 됨!

<ProductJpaEntity, Long>
→ 어떤 Entity를, 어떤 타입의 PK로 관리할지
*/
