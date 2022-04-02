package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

public interface VolunteerService {

    Long register(VolunteerDto volunteerDto, Long memberSeq);

    Long updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    void remove(Long volunteerSeq, Long memberSeq);

    Volunteer changeVolunteerStatus(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    VolunteerDto.Detail volunteerDetail(Long volunteerSeq);

    VolunteerDto.VolunteerListPage getPage(Pageable pageable, VolunteerDto.SearchInfo searchInfo);

    VolunteerDto.VolunteerListPage getPageAll(Pageable pageable);

    Long requestVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth);

    Long modifyVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth);

    VolunteerDto.VolunteerAuthDetail getVolunteerAuth(Long memberSeq, Long volunteerSeq);

    Member getMemberByVolunteerSeq(Long volunteerSeq);
}
