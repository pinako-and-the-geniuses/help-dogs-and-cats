package com.ssafy.a302.domain.community.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.community.service.dto.QCommunityDto_ForPage;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.member.service.dto.QProfileDto_Community;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.community.entity.QCommunity.*;

public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommunityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countAllByCategoryAndSearchAndKeyword(Community.Category category, String search, String keyword) {
        return queryFactory
                .select(community.count().intValue())
                .from(community)
                .where(
                        community.isDeleted.isFalse(),
                        categoryEq(category),
                        searchEq(search, keyword))
                .fetchOne();
    }

    @Override
    public Integer countAllByMemberSeq(Long memberSeq) {
        return queryFactory
                .select(community.count().intValue())
                .from(community)
                .where(
                        community.isDeleted.isFalse(),
                        community.member.seq.eq(memberSeq))
                .fetchOne();
    }

    @Override
    public Integer countAllByMemberSeqAndCategoryEqReport(Long memberSeq) {
        return queryFactory
                .select(community.count().intValue())
                .from(community)
                .where(
                        community.isDeleted.isFalse(),
                        community.member.seq.eq(memberSeq),
                        community.category.eq(Community.Category.REPORT))
                .fetchOne();
    }

    @Override
    public Optional<List<CommunityDto.ForPage>> findCommunitiesForPage(Pageable pageable, Community.Category category, String search, String keyword) {
        List<CommunityDto.ForPage> list = queryFactory
                .select(new QCommunityDto_ForPage(
                        community.seq,
                        community.title,
                        community.viewCount.intValue(),
                        community.category,
                        community.member.seq,
                        community.member.detail.nickname,
                        community.createdDate))
                .from(community)
                .where(
                        community.isDeleted.isFalse(),
                        categoryEq(category),
                        searchEq(search, keyword))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(community.createdDate.desc())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<ProfileDto.Community>> findCommunitiesForProfile(Long memberSeq, Pageable pageable) {
        List<ProfileDto.Community> list = queryFactory
                .select(new QProfileDto_Community(
                        community.seq,
                        community.title,
                        community.category,
                        community.createdDate))
                .from(community)
                .where(community.member.seq.eq(memberSeq).and(community.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(community.createdDate.desc())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    private BooleanExpression categoryEq(Community.Category category) {
        return category != null ? community.category.eq(category) : null;
    }

    private BooleanExpression searchEq(String search, String keyword) {
        if (StringUtils.hasText(search)) {
            if ("title".equalsIgnoreCase(search)) {
                return community.title.contains(keyword);
            } else if ("writer".equalsIgnoreCase(search)) {
                return community.member.detail.nickname.eq(keyword);
            }
        } else if (StringUtils.hasText(keyword)) {
            return community.title.contains(keyword).or(community.member.detail.nickname.eq(keyword));
        }

        return null;
    }
}
