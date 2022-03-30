package com.ssafy.a302.domain.volunteer.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.admin.service.dto.QVolunteerAuthDto_VolunteerAuthPage_VolunteerAuthForPage;
import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.service.dto.QVolunteerDto_VolunteerAuthDetail_Participant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.enums.Status;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.volunteer.entity.QVolunteerAuth.*;
import static com.ssafy.a302.domain.volunteer.entity.QVolunteerParticipant.*;

public class VolunteerAuthRepositoryImpl implements VolunteerAuthRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public VolunteerAuthRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<VolunteerDto.VolunteerAuthDetail.Participant>> findVolunteerParticipantsDtoByVolunteerSeq(Long volunteerSeq) {
        List<VolunteerDto.VolunteerAuthDetail.Participant> list = queryFactory
                .select(new QVolunteerDto_VolunteerAuthDetail_Participant(
                        volunteerParticipant.member.seq,
                        volunteerParticipant.member.detail.nickname,
                        volunteerParticipant.approve))
                .from(volunteerParticipant)
                .where(volunteerParticipant.volunteer.seq.eq(volunteerSeq))
                .orderBy(volunteerParticipant.member.seq.asc())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    @Override
    public Integer countAllByStatus(String search) {
        JPAQuery<Integer> query = queryFactory
                .select(volunteerAuth.count().intValue())
                .from(volunteerAuth)
                .where(volunteerAuth.volunteer.isDeleted.isFalse());

        if (search.equals("auth")) {
            query.where(volunteerAuth.status.eq(Status.DONE));
        } else {
            query.where(volunteerAuth.status.ne(Status.DONE));
        }

        return query.fetchOne();
    }

    @Override
    public Optional<List<VolunteerAuthDto.VolunteerAuthPage.VolunteerAuthForPage>> findVolunteerAuthForPageDto(Pageable pageable, String search) {
        JPAQuery<VolunteerAuthDto.VolunteerAuthPage.VolunteerAuthForPage> query = queryFactory
                .select(new QVolunteerAuthDto_VolunteerAuthPage_VolunteerAuthForPage(
                        volunteerAuth.seq,
                        volunteerAuth.volunteer.title,
                        volunteerAuth.status))
                .from(volunteerAuth)
                .where(volunteerAuth.volunteer.isDeleted.isFalse())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(volunteerAuth.seq.desc());

        if (search.equals("auth")) {
            query.where(volunteerAuth.status.eq(Status.DONE));
        } else {
            query.where(volunteerAuth.status.ne(Status.DONE));
        }

        List<VolunteerAuthDto.VolunteerAuthPage.VolunteerAuthForPage> list = query.fetch();
        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }
}
