package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VolunteerAuthRepositoryCustom {

    Optional<List<VolunteerDto.VolunteerAuthDetail.Participant>> findVolunteerParticipantsDtoByVolunteerSeq(Long volunteerSeq);

    Integer countAllByStatus(String search);

    Optional<List<VolunteerAuthDto.VolunteerAuthPage.VolunteerAuthForPage>> findVolunteerAuthForPageDto(Pageable pageable, String search);
}
