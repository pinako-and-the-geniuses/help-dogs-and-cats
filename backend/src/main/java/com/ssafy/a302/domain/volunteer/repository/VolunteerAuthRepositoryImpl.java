package com.ssafy.a302.domain.volunteer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.volunteer.service.dto.QVolunteerDto_VolunteerAuthDetail_Participant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
}
