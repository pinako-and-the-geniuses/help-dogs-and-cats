package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>, VolunteerRepositoryCustom {

    @Query("SELECT v FROM Volunteer v WHERE v.seq = :seq AND v.isDeleted = FALSE")
    Optional<Volunteer> findBySeq(@Param(value = "seq") Long seq);
}
