package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long>, MemberBadgeRepositoryCustom {

}
