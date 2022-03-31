package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.Badge;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepositoryCustom {

    Optional<List<Badge>> findBadgeAllByMemberSeq(Long memberSeq);

    boolean existsByMemberSeqAndBadgeName(Long memberSeq, String badgeName);
}
