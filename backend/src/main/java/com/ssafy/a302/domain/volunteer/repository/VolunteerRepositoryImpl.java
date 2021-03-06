package com.ssafy.a302.domain.volunteer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.member.service.dto.QProfileDto_Volunteer;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.service.dto.QVolunteerDto_ForPage;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ssafy.a302.domain.volunteer.entity.QVolunteer.volunteer;
import static com.ssafy.a302.domain.volunteer.entity.QVolunteerAuth.*;
import static com.ssafy.a302.domain.volunteer.entity.QVolunteerParticipant.*;

public class VolunteerRepositoryImpl implements VolunteerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public VolunteerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countAllByKeyword(String keyword) {
        JPAQuery<Integer> query = queryFactory
                .select(volunteer.count().intValue())
                .from(volunteer)
                .where(volunteer.isDeleted.isFalse());

        if (StringUtils.hasText(keyword)) {
            query.where(volunteer.title.contains(keyword));
        }

        return query.fetchOne();
    }

    @Override
    public Integer countAllBySearchInfo(VolunteerDto.SearchInfo searchInfo) {
        JPAQuery<Integer> query = queryFactory
                .select(volunteer.count().intValue())
                .from(volunteer)
                .where(
                        volunteer.isDeleted.isFalse(),
                        volunteer.endDate.loe(searchInfo.getEndDate())
                );

        if (!searchInfo.getActivityArea().equals("??????")) {
            query.where(volunteer.activityArea.eq(searchInfo.getActivityArea()));
        }

        if (searchInfo.getAdmit()) {
            query.where(volunteer.authTime.ne("0"));
        }

        if (StringUtils.hasText(searchInfo.getKeyword())) {
            query.where(volunteer.title.contains(searchInfo.getKeyword()));
        }

        return query.fetchOne();
    }

    @Override
    public Integer countAll() {
        return queryFactory
                .select(volunteer.count().intValue())
                .from(volunteer)
                .where(
                        volunteer.isDeleted.isFalse())
                .fetchOne();
    }

    @Override
    public Optional<List<VolunteerDto.ForPage>> findVolunteersForPage(Pageable pageable, VolunteerDto.SearchInfo searchInfo) {
        JPAQuery<VolunteerDto.ForPage> query = queryFactory
                .select(new QVolunteerDto_ForPage(
                        volunteer.seq,
                        volunteer.status,
                        volunteer.title,
                        volunteer.maxParticipantCount.intValue(),
                        volunteer.member.detail.nickname,
                        volunteer.member.seq,
                        volunteer.endDate,
                        volunteer.createdDate
                ))
                .from(volunteer)
                .where(
                        volunteer.isDeleted.isFalse(),
                        volunteer.endDate.loe(searchInfo.getEndDate())
                )
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(volunteer.seq.desc());

        if (!searchInfo.getActivityArea().equals("??????")) {
            query.where(volunteer.activityArea.eq(searchInfo.getActivityArea()));
        }

        if (searchInfo.getAdmit()) {
            query.where(volunteer.authTime.ne("0"));
        }

        if (StringUtils.hasText(searchInfo.getKeyword())) {
            query.where(volunteer.title.contains(searchInfo.getKeyword()));
        }

        List<VolunteerDto.ForPage> list = query.fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<VolunteerDto.ForPage>> findVolunteersForPageAll(Pageable pageable) {
        List<VolunteerDto.ForPage> list = queryFactory
                .select(new QVolunteerDto_ForPage(
                        volunteer.seq,
                        volunteer.status,
                        volunteer.title,
                        volunteer.maxParticipantCount.intValue(),
                        volunteer.member.detail.nickname,
                        volunteer.member.seq,
                        volunteer.endDate,
                        volunteer.createdDate
                ))
                .from(volunteer)
                .where(
                        volunteer.isDeleted.isFalse())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(volunteer.createdDate.desc())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<ProfileDto.Volunteer>> findVolunteersForProfile(Long memberSeq, Pageable pageable) {
        List<ProfileDto.Volunteer> list = queryFactory
                .select(new QProfileDto_Volunteer(
                        volunteer.seq,
                        volunteer.member.seq,
                        volunteer.title,
                        volunteer.status,
                        volunteer.volunteerAuth.status,
                        volunteer.createdDate))
                .from(volunteer)
                .join(volunteer.volunteerParticipants, volunteerParticipant)
                .leftJoin(volunteer.volunteerAuth, volunteerAuth)
                .where(volunteerParticipant.member.seq.eq(memberSeq).and(volunteer.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(volunteer.createdDate.desc())
                .fetch();

        if (list.size() == 0) {
            list = null;
        }

        return Optional.ofNullable(list);
    }

    @Override
    public void updateStatusRecruitingToOngoing() {
        queryFactory
                .update(volunteer)
                .set(volunteer.status, Volunteer.Status.ONGOING)
                .where(
                        volunteer.endDate.before(LocalDate.now()),
                        volunteer.isDeleted.isFalse()
                )
                .execute();
    }

    private BooleanExpression searchEq(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return volunteer.title.contains(keyword).or(volunteer.member.detail.nickname.eq(keyword));
        }

        return null;
    }
}
