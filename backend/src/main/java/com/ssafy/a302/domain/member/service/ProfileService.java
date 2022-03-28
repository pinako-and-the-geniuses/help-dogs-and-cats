package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

    MemberDto.Profile getProfile(Long memberSeq);

    ProfileDto.CommunityPage getCommunities(Long memberSeq, Pageable pageable);
}
