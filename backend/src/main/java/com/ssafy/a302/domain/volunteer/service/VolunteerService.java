package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.springframework.data.domain.Pageable;

public interface VolunteerService {

    VolunteerDto.Response register(VolunteerDto volunteerDto, Long memberSeq);

    VolunteerDto.Response updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    Volunteer deleteVolunteer(Long volunteerSeq, Long memberSeq);

    Volunteer changeVolunteerStatus(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq);

    VolunteerDto.Detail volunteerDetail(Long volunteerSeq);

    VolunteerDto.VolunteerListPage getPage(Pageable pageable, String keyword);

}
