package com.ssafy.a302.domain.volunteer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.global.enums.Status;

import javax.persistence.EntityManager;

import static com.ssafy.a302.domain.volunteer.entity.QVolunteerParticipant.*;

public class VolunteerParticipantRepositoryImpl implements VolunteerParticipantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public VolunteerParticipantRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countByMemberSeqAndVolunteerStatusEqDone(Long memberSeq) {
        return queryFactory
                .select(volunteerParticipant.count().intValue())
                .from(volunteerParticipant)
                .where(
                        volunteerParticipant.volunteer.volunteerAuth.status.eq(Status.DONE),
                        volunteerParticipant.member.seq.eq(memberSeq))
                .fetchOne();
    }
}
