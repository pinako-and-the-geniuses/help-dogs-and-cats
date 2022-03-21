package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.service.dto.MemberDto;

public interface MemberService {

    Member findMemberByEmail(String email);

    MemberDto.Response register(MemberDto memberDto);

    boolean isExistsEmail(String email);

    boolean isExistsNickname(String nickname);
}
