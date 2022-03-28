package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepositoryCustom {

    Integer countAllByKeyword(String keyword);

    Optional<List<VolunteerDto.ForPage>> findVolunteersForPage(Pageable pageable, String keyword);

    Optional<List<ProfileDto.Volunteer>> findVolunteersForProfile(Long memberSeq, Pageable pageable);
}
