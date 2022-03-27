package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityCommentDto;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import org.springframework.data.domain.Pageable;

public interface CommunityService {

    Long register(CommunityDto communityDto, Long memberSeq);

    Long modify(Long communitySeq, CommunityDto communityDto, Long memberSeq);

    CommunityDto.CommunityListPage getPage(Pageable pageable, Community.Category category, String search, String keyword);

    Long registerComment(Long communitySeq, CommunityCommentDto.RegisterInfo registerInfo, Long memberSeq);

    void removeComment(Long communitySeq, Long commentSeq, Long memberSeq);

    void remove(Long communitySeq, Long memberSeq);
}
