package com.ssafy.a302.domain.badge.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.badge.service.dto.QBadgeDto_ForProfile;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.badge.entity.QBadge.*;

public class BadgeRepositoryImpl implements BadgeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BadgeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<BadgeDto.ForProfile>> findBadgesForProfileDto() {
        List<BadgeDto.ForProfile> list = queryFactory
                .select(new QBadgeDto_ForProfile(
                        badge.seq,
                        badge.name,
                        badge.content,
                        badge.howToGet,
                        badge.imageFilename
                ))
                .from(badge)
                .orderBy(badge.seq.asc())
                .fetch();

        if (list.isEmpty()) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
