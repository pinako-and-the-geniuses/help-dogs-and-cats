package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.service.dto.MemberDto;

public interface ProfileService {

    MemberDto.Profile getProfile(Long memberSeq);
}
