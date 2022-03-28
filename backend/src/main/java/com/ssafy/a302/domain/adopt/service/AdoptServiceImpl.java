package com.ssafy.a302.domain.adopt.service;

import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.adopt.service.dto.AdoptDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptServiceImpl implements AdoptService {

    private final AdoptAuthRepository adoptAuthRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long requestAdoptAuth(Long memberSeq, AdoptDto.AdoptAuth adoptAuth) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        AdoptAuth savedAdoptAuth = adoptAuthRepository.save(AdoptAuth.builder()
                .member(findMember)
                .title(adoptAuth.getTitle())
                .content(adoptAuth.getContent())
                .build());

        return savedAdoptAuth.getSeq();
    }

    @Transactional
    @Override
    public Long modifyAdoptAuth(Long memberSeq, Long adoptAuthSeq, AdoptDto.AdoptAuth adoptAuth) {
        AdoptAuth findAdoptAuth = adoptAuthRepository.findBySeq(adoptAuthSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findAdoptAuth.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        findAdoptAuth.modify(adoptAuth);

        return findAdoptAuth.getSeq();
    }

    @Override
    public AdoptDto.AdoptAuth getAdoptAuth(Long memberSeq, Long adoptAuthSeq) {
        AdoptAuth findAdoptAuth = adoptAuthRepository.findBySeq(adoptAuthSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findAdoptAuth.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        return findAdoptAuth.toDto();
    }
}
