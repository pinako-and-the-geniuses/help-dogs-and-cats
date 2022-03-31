package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolunteerCommentRepositoryCustom {
    @Query("SELECT vc FROM VolunteerComment vc WHERE vc.volunteer.seq = :seq AND vc.isDeleted = FALSE")
    Optional<List<VolunteerComment>> findCommentsByVolunteerSeq(@Param(value = "seq") Long seq);
}
