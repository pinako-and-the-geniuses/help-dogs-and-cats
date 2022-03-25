package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>, VolunteerRepositoryCustom {

    Optional<Volunteer> findBySeq(Long seq);
}
