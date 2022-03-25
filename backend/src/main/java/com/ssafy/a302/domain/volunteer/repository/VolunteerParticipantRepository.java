package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolunteerParticipantRepository extends JpaRepository<VolunteerParticipant, Long> {

    Optional<VolunteerParticipant> findByMemberSeqAndVolunteerSeq(Long memberSeq, Long volunteerSeq);

    @Query("SELECT v FROM VolunteerParticipant v WHERE v.volunteer.seq = :seq")
    Optional<List<VolunteerParticipant>> findVolunteerParticipantBySeq(@Param(value = "seq") Long seq);
}
