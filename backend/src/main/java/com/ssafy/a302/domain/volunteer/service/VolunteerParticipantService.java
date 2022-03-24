package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerParticipantRequestDto;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerParticipantDto;

import java.util.List;

public interface VolunteerParticipantService {

    VolunteerParticipantDto.Response applyVolunteer(Long volunteerSeq, Long memberSeq);

    void cancelVolunteer(Long volunteerSeq, Long memberSeq);

    List<VolunteerParticipantServiceImpl.SimpleVolunteerParticipantDto> getVolunteerParticipantList(Long volunteerSeq, Long memberSeq);

    VolunteerParticipant changeParticipantIsApprove(VolunteerParticipantDto volunteerParticipantDto, Long volunteerSeq, Long memberSeq, Long memberCreatorSeq);

    VolunteerParticipant deleteVolunteerParticipant(Long volunteerSeq, Long memberSeq, Long memberCreatorSeq);
}
