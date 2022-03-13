package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findMemberByEmail(String email);
}
