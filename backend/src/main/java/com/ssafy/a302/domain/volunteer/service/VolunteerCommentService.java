package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerCommentDto;

public interface VolunteerCommentService {
    Long registerVolunteerComment(Long volunteerSeq, Long memberSeq, VolunteerCommentDto.RegisterInfo registerInfo);

    VolunteerComment deleteVolunteerComment(Long volunteerSeq, Long commentsSeq, Long memberSeq);
}
