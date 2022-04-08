package com.ssafy.a302.domain.adopt.repository;

import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdoptAuthRepositoryCustom {

    Integer countAllByMemberSeq(Long memberSeq);

    Integer countAllByMemberSeqAndStatusEqDone(Long memberSeq);

    Optional<List<ProfileDto.Adopt>> findAdoptsForProfile(Long memberSeq, Pageable pageable);

    Integer countAllByStatus(String search);

    Optional<List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage>> findAdoptAuthForPageDto(Pageable pageable, String search);
}
