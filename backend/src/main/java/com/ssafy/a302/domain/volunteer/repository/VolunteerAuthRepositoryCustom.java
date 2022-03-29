package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;

import java.util.List;
import java.util.Optional;

public interface VolunteerAuthRepositoryCustom {

    Optional<List<VolunteerDto.VolunteerAuthDetail.Participant>> findVolunteerParticipantsDtoByVolunteerSeq(Long volunteerSeq);
}
