package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerCommentRepository extends JpaRepository<VolunteerComment, Long> {
}
