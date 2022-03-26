package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query("SELECT c FROM Community c WHERE c.seq = :seq AND c.isDeleted = FALSE")
    Optional<Community> findBySeq(@Param(value = "seq") Long seq);
}
