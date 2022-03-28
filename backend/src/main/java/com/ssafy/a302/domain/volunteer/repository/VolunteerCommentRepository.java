package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.community.entity.CommunityComment;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolunteerCommentRepository extends JpaRepository<VolunteerComment, Long> {
    List<VolunteerComment> findByVolunteerSeq(Long volunteerSeq);

    @Query("SELECT vc FROM VolunteerComment vc WHERE vc.seq = :seq AND vc.isDeleted = FALSE")
    Optional<VolunteerComment> findBySeq(@Param(value = "seq") Long seq);
}
