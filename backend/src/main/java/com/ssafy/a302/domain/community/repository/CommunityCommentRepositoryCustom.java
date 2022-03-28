package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.CommunityComment;

import java.util.List;
import java.util.Optional;

public interface CommunityCommentRepositoryCustom {

    Optional<List<CommunityComment>> findCommentsByCommunitySeq(Long communitySeq);
}
