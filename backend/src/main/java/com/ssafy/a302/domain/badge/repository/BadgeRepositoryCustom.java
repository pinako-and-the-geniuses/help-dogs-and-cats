package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.service.dto.BadgeDto;

import java.util.List;
import java.util.Optional;

public interface BadgeRepositoryCustom {

    Optional<List<BadgeDto.ForProfile>> findBadgesForProfileDto();
}
