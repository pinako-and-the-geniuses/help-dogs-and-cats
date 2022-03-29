package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

public interface VolunteerService {

    Long register(VolunteerDto volunteerDto, Long memberSeq);

    VolunteerDto.Response updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    Volunteer deleteVolunteer(Long volunteerSeq, Long memberSeq);

    Volunteer changeVolunteerStatus(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    VolunteerDto.Detail volunteerDetail(Long volunteerSeq);

    VolunteerDto.VolunteerListPage getPage(Pageable pageable, String keyword);

    Long requestVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth);

    Long modifyVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth);

    VolunteerDto.VolunteerAuthDetail getVolunteerAuth(Long memberSeq, Long volunteerSeq);
}
