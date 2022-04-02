package com.ssafy.a302.domain.volunteer.repository;

public interface VolunteerParticipantRepositoryCustom {

    Integer countByMemberSeqAndVolunteerStatusEqDone(Long memberSeq);
}
