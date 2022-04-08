package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VolunteerAuthRepository extends JpaRepository<VolunteerAuth, Long>, VolunteerAuthRepositoryCustom {

    boolean existsByVolunteerSeq(Long volunteerSeq);

    Optional<VolunteerAuth> findByVolunteerSeq(Long volunteerSeq);

    @Query("SELECT COUNT(va) FROM VolunteerAuth va WHERE va.status = 'REQUEST'")
    Integer countAllByStatusEqRequest();
}
