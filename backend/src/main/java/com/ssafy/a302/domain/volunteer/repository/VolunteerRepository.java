package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>, VolunteerRepositoryCustom {

    @Query("SELECT v FROM Volunteer v WHERE v.seq = :seq AND v.isDeleted = FALSE")
    Optional<Volunteer> findBySeq(@Param(value = "seq") Long seq);

    @Query("SELECT COUNT(v) FROM Volunteer v WHERE v.member.seq = :memberSeq AND v.volunteerAuth.status = 'DONE'")
    Integer countByMemberSeqAndVolunteerStatusEqDone(@Param(value = "memberSeq") Long memberSeq);

    @Query("SELECT v.member FROM Volunteer v WHERE v.seq = :volunteerSeq AND v.isDeleted = FALSE")
    Optional<Member> findMemberByVolunteerSeq(@Param(value = "volunteerSeq") Long volunteerSeq);
}
