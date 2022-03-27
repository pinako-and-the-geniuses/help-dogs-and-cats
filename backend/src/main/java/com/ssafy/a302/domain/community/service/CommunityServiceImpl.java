package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final MemberRepository memberRepository;

    @Override
    public Long register(CommunityDto communityDto, Long memberSeq) {
        Member writer = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        Community newCommunity = communityDto.toEntity(writer);
        return communityRepository.save(newCommunity).getSeq();
    }

    @Override
    public CommunityDto.CommunityListPage getPage(Pageable pageable, Community.Category category, String search, String keyword) {
        Integer totalCount = communityRepository.countAllByCategoryAndSearchAndKeyword(category, search, keyword);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<CommunityDto.ForPage> communitiesForPage = communityRepository.findCommunitiesForPage(pageable, category, search, keyword)
                .orElse(null);

        return CommunityDto.CommunityListPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .communitiesForPage(communitiesForPage)
                .currentPageNumber(pageable.getPageNumber())
                .build();
    }
}
