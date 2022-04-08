package com.ssafy.a302.domain.community.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.community.entity.CommunityComment;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.community.entity.QCommunityComment.communityComment;

public class CommunityCommentRepositoryImpl implements CommunityCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommunityCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<CommunityComment>> findCommentsByCommunitySeq(Long communitySeq) {
        List<CommunityComment> list = queryFactory
                .selectFrom(communityComment)
                .leftJoin(communityComment.parent).fetchJoin()
                .where(communityComment.community.seq.eq(communitySeq).and(communityComment.isDeleted.isFalse()))
                .orderBy(
                        communityComment.parent.seq.asc().nullsFirst(),
                        communityComment.createdDate.asc()
                ).fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
