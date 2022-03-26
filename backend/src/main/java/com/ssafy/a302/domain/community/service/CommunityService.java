package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.service.dto.CommunityDto;

public interface CommunityService {

    Long register(CommunityDto communityDto, Long memberSeq);
}
