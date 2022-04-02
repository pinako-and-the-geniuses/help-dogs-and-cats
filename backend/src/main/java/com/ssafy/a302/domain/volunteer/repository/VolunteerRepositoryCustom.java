package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepositoryCustom {

    Integer countAllByKeyword(String keyword);

    Integer countAllBySearchInfo(VolunteerDto.SearchInfo searchInfo);

    Integer countAll();

    Optional<List<VolunteerDto.ForPage>> findVolunteersForPage(Pageable pageable, VolunteerDto.SearchInfo searchInfo);

    Optional<List<VolunteerDto.ForPage>> findVolunteersForPageAll(Pageable pageable);

    Optional<List<ProfileDto.Volunteer>> findVolunteersForProfile(Long memberSeq, Pageable pageable);

    void updateStatusRecruitingToOngoing();
}
