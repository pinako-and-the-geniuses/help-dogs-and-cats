package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {

    @Query("SELECT cc FROM CommunityComment cc WHERE cc.seq = :seq AND cc.isDeleted = FALSE")
    Optional<CommunityComment> findBySeq(@Param(value = "seq") Long seq);
}
