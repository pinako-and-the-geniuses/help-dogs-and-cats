package com.ssafy.a302.domain.adopt.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.member.service.dto.QProfileDto_Adopt;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.adopt.entity.QAdoptAuth.*;

public class AdoptAuthRepositoryImpl implements AdoptAuthRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdoptAuthRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countAllByMemberSeq(Long memberSeq) {
        return queryFactory
                .select(adoptAuth.count().intValue())
                .from(adoptAuth)
                .where(
                        adoptAuth.member.seq.eq(memberSeq))
                .fetchOne();
    }

    @Override
    public Optional<List<ProfileDto.Adopt>> findAdoptsForProfile(Long memberSeq, Pageable pageable) {
        List<ProfileDto.Adopt> list = queryFactory
                .select(new QProfileDto_Adopt(
                        adoptAuth.seq,
                        adoptAuth.title,
                        adoptAuth.status))
                .from(adoptAuth)
                .where(adoptAuth.member.seq.eq(memberSeq))
                .orderBy(adoptAuth.createdDate.desc())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
