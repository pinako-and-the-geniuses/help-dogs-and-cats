package com.ssafy.a302.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.member.entity.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.a302.domain.member.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.email.eq(email).and(member.isDeleted.isFalse()))
                        .fetchOne());
    }
}
