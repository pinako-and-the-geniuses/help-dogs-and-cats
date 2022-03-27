package com.ssafy.a302.domain.badge.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.badge.entity.Badge;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.badge.entity.QBadge.*;
import static com.ssafy.a302.domain.badge.entity.QMemberBadge.*;

public class MemberBadgeRepositoryImpl implements MemberBadgeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberBadgeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<Badge>> findBadgeAllByMemberSeq(Long memberSeq) {
        List<Badge> list = queryFactory
                .select(memberBadge.badge)
                .from(memberBadge)
                .join(memberBadge.badge, badge)
                .where(memberBadge.member.seq.eq(memberSeq))
                .orderBy(memberBadge.badge.seq.asc())
                .fetch();

        if (list.isEmpty()) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
