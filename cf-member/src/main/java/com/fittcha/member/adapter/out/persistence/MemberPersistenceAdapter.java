package com.fittcha.member.adapter.out.persistence;

import com.fittcha.member.application.port.out.LoadMemberPort;
import com.fittcha.member.application.port.out.SaveMemberPort;
import com.fittcha.member.application.port.out.UpdateMemberPort;
import com.fittcha.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 회원 Persistence Adapter
 *
 * implements SaveMemberPort, LoadMemberPort, UpdateMemberPort
 * → Port Out 인터페이스 구현
 * → Service는 이 인터페이스만 알면 됨
 *
 * 흐름:
 * 1. Domain → Entity 변환 (toJpaEntity)
 * 2. DB 저장 (JpaRepository.save)
 * 3. Entity → Domain 변환 (toDomain)
 * 4. 리턴
 *
 * Service는 JPA 몰라도 됨!
 * → Hexagonal 핵심: 도메인이 인프라에 의존 안 함
 */
@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SaveMemberPort, LoadMemberPort, UpdateMemberPort {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        MemberJpaEntity entity = memberMapper.toJpaEntity(member);
        MemberJpaEntity savedEntity = memberJpaRepository.save(entity);
        return memberMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id)
                .map(memberMapper::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email)
                .map(memberMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberJpaRepository.existsByEmail(email);
    }

    /**
     * 회원 수정
     *
     * JPA 더티체킹 활용
     * → 엔티티 조회 후 값 변경하면
     * → 트랜잭션 끝날 때 자동 UPDATE
     *
     * 주의: 더티체킹은 @Transactional 안에서만 동작
     * Service에 @Transactional 추가 필요!
     */
    @Override
    public Member update(Member member) {
        MemberJpaEntity entity = memberJpaRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + member.getId()));

        entity.updateProfile(member.getName(), member.getPhone());

        return memberMapper.toDomain(entity);
    }
}
