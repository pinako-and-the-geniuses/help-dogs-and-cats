package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    boolean existsByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.seq = :seq AND m.isDeleted = FALSE")
    Optional<Member> findMemberBySeq(@Param(value = "seq") Long seq);

    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.isDeleted = FALSE")
    Optional<Member> findMemberByEmail(@Param(value = "email") String email);
}
