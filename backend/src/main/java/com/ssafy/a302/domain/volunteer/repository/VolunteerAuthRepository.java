package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerAuthRepository extends JpaRepository<VolunteerAuth, Long> {

    boolean existsByVolunteerSeq(Long volunteerSeq);

    Optional<VolunteerAuth> findByVolunteerSeq(Long volunteerSeq);
}
