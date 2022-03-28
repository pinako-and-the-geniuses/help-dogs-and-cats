package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommunityRepositoryCustom {

    Integer countAllByCategoryAndSearchAndKeyword(Community.Category category, String search, String keyword);

    Optional<List<CommunityDto.ForPage>> findCommunitiesForPage(Pageable pageable, Community.Category category, String search, String keyword);

    Integer countAllByMemberSeq(Long memberSeq);

    Optional<List<ProfileDto.Community>> findCommunitiesForProfile(Long memberSeq, Pageable pageable);
}
