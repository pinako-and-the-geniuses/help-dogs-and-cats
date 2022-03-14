package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.Member;

public interface MemberService {

    Member findMemberByEmail(String email);
}
