package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerCommentRepository extends JpaRepository<VolunteerComment, Long> {
    List<VolunteerComment> findByVolunteerSeq(Long volunteerSeq);
}
