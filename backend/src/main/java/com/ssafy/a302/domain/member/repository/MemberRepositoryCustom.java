package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.service.dto.MemberDto;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<MemberDto> findMemberDtoBySeq(Long memberSeq);

    Optional<String> findEmailByTel(String tel);
}
