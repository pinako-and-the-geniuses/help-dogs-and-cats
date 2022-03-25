package com.ssafy.a302.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.member.service.dto.QMemberDto;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.a302.domain.member.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<MemberDto> findMemberDtoBySeq(Long memberSeq) {
        return Optional.ofNullable(queryFactory
                .select(new QMemberDto(
                        member.seq,
                        member.email,
                        member.password,
                        member.role,
                        member.detail.nickname,
                        member.detail.tel,
                        member.detail.activityArea,
                        member.detail.exp,
                        member.detail.profileImageFilename
                ))
                .from(member)
                .where(member.seq.eq(memberSeq).and(member.isDeleted.isFalse()))
                .fetchOne());
    }
}
