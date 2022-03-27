package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import org.springframework.data.domain.Pageable;

public interface CommunityService {

    Long register(CommunityDto communityDto, Long memberSeq);

    CommunityDto.CommunityListPage getPage(Pageable pageable, Community.Category category, String search, String keyword);
}
