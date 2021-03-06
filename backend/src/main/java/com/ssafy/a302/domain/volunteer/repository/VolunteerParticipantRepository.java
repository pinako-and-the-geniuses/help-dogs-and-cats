package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolunteerParticipantRepository extends JpaRepository<VolunteerParticipant, Long>, VolunteerParticipantRepositoryCustom {

    Optional<VolunteerParticipant> findByMemberSeqAndVolunteerSeq(Long memberSeq, Long volunteerSeq);

    @Query("SELECT v FROM VolunteerParticipant v WHERE v.volunteer.seq = :seq")
    Optional<List<VolunteerParticipant>> findVolunteerParticipantBySeq(@Param(value = "seq") Long seq);

    Integer countAllByMemberSeq(Long memberSeq);

    @Query("SELECT v FROM VolunteerParticipant v WHERE v.volunteer.seq= :seq AND v.approve=True")
    Optional<List<VolunteerParticipant>> findAllByVolunteerSeq(@Param(value = "seq") Long seq);

    @Query("SELECT vp.member.seq FROM VolunteerParticipant vp WHERE vp.volunteer.seq = :volunteerSeq")
    Optional<List<Long>> findParticipantSeqAllByVolunteerSeq(@Param(value = "volunteerSeq") Long  volunteerSeq);

    @Query("SELECT COUNT(vp) FROM VolunteerParticipant vp WHERE vp.volunteer.seq = :volunteerSeq AND vp.approve = True")
    Integer countAllByVolunteerSeqAndApproveEqTrue(@Param(value = "volunteerSeq") Long volunteerSeq);
}
