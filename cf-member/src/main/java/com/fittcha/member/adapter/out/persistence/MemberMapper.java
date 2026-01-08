package com.fittcha.member.adapter.out.persistence;

import com.fittcha.member.domain.Member;
import org.springframework.stereotype.Component;

/**
 * Domain ↔ JPA Entity 변환 Mapper
 */
@Component
public class MemberMapper {

    /**
     * Domain → JPA Entity 변환 (저장용)
     */
    public MemberJpaEntity toJpaEntity(Member member) {
        return MemberJpaEntity.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .grade(member.getGrade())
                .status(member.getStatus())
                .loginType(member.getLoginType())
                .socialId(member.getSocialId())
                .build();
    }

    /**
     * JPA Entity → Domain 변환 (조회용)
     */
    public Member toDomain(MemberJpaEntity entity) {
        return Member.of(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getPhone(),
                entity.getGrade(),
                entity.getStatus(),
                entity.getLoginType(),
                entity.getSocialId());
    }
}
