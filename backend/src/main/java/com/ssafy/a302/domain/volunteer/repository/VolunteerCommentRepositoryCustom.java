package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;

import java.util.List;
import java.util.Optional;

public interface VolunteerCommentRepositoryCustom {
    Optional<List<VolunteerComment>> findCommentsByVolunteerSeq(Long volunteerSeq);
}
