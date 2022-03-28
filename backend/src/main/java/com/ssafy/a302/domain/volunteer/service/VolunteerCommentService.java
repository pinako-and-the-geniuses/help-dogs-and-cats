package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerCommentDto;

public interface VolunteerCommentService {
    VolunteerCommentDto.Response registerVolunteerComment(Long volunteerSeq, Long memberSeq, VolunteerCommentDto volunteerCommentDto);

    VolunteerComment deleteVolunteerComment(Long volunteerSeq, Long commentsSeq, Long memberSeq);
}
