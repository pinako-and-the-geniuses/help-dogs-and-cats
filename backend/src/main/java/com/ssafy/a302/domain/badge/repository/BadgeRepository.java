package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long>, BadgeRepositoryCustom {

    Optional<Badge> findByName(String name);
}
