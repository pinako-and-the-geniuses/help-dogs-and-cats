package com.ssafy.a302.domain.adopt.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.QAdoptAuthDto_AdoptAuthPage_AdoptAuthForPage;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.member.service.dto.QProfileDto_Adopt;
import com.ssafy.a302.global.enums.Status;
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

    @Override
    public Integer countAllByStatus(String search) {
        JPAQuery<Integer> query = queryFactory
                .select(adoptAuth.count().intValue())
                .from(adoptAuth);

        if (search.equals("auth")) {
            query.where(adoptAuth.status.eq(Status.DONE));
        } else if (search.equals("not-auth")) {
            query.where(adoptAuth.status.ne(Status.DONE));
        }

        return query.fetchOne();
    }

    @Override
    public Optional<List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage>> findAdoptAuthForPageDto(Pageable pageable, String search) {
        JPAQuery<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage> query = queryFactory
                .select(new QAdoptAuthDto_AdoptAuthPage_AdoptAuthForPage(
                        adoptAuth.seq,
                        adoptAuth.title,
                        adoptAuth.status))
                .from(adoptAuth)
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(adoptAuth.seq.desc());

        if (search.equals("auth")) {
            query.where(adoptAuth.status.eq(Status.DONE));
        } else if (search.equals("not-auth")) {
            query.where(adoptAuth.status.ne(Status.DONE));
        }

        List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage> list = query.fetch();
        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
