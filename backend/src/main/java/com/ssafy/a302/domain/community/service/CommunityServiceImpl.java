package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.entity.CommunityComment;
import com.ssafy.a302.domain.community.repository.CommunityCommentRepository;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.community.service.dto.CommunityCommentDto;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final CommunityCommentRepository communityCommentRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long register(CommunityDto communityDto, Long memberSeq) {
        Member writer = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        Community newCommunity = communityDto.toEntity(writer);
        return communityRepository.save(newCommunity).getSeq();
    }

    @Transactional
    @Override
    public Long modify(Long communitySeq, CommunityDto communityDto, Long memberSeq) {
        Community findCommunity = communityRepository.findBySeq(communitySeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findCommunity.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        findCommunity.modify(communityDto);
        return findCommunity.getSeq();
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

    @Transactional
    @Override
    public Long registerComment(Long communitySeq, CommunityCommentDto.RegisterInfo registerInfo, Long memberSeq) {
        Community findCommunity = communityRepository.findBySeq(communitySeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        CommunityComment newCommunityComment = CommunityComment.builder()
                .community(findCommunity)
                .member(findMember)
                .content(registerInfo.getContent())
                .build();

        Long parentSeq = registerInfo.getParentSeq();
        if (parentSeq != null) {
            CommunityComment parentComment = communityCommentRepository.findBySeq(parentSeq)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

            newCommunityComment.createParent(parentComment);
        }

        return communityCommentRepository.save(newCommunityComment).getSeq();
    }

    @Transactional
    @Override
    public void removeComment(Long communitySeq, Long commentSeq, Long memberSeq) {
        CommunityComment findComment = communityCommentRepository.findBySeq(commentSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findComment.getCommunity().getSeq().equals(communitySeq)) {
            throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
        }

        if (!findComment.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        findComment.delete();
    }

    @Transactional
    @Override
    public void remove(Long communitySeq, Long memberSeq) {
        Community findCommunity = communityRepository.findBySeq(communitySeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findCommunity.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        findCommunity.delete();
    }

    @Transactional
    @Override
    public CommunityDto.Detail detail(Long communitySeq) {
        Community findCommunity = communityRepository.findBySeq(communitySeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        findCommunity.incrementViewCount();

        Member writer = findCommunity.getMember();
        List<CommunityComment> communityComments = communityCommentRepository.findCommentsByCommunitySeq(communitySeq)
                .orElse(null);

        /**
         * 대댓글 계층형 구조 생성
         */
        List<CommunityCommentDto.ForDetail> commentsForDetail = convertNestedStructure((communityComments));

        return CommunityDto.Detail.create(findCommunity, writer, commentsForDetail);
    }

    private List<CommunityCommentDto.ForDetail> convertNestedStructure(List<CommunityComment> comments) {
        if (comments == null) return null;

        List<CommunityCommentDto.ForDetail> result = new ArrayList<>();
        Map<Long, CommunityCommentDto.ForDetail> map = new HashMap<>();
        comments.forEach(comment -> {
            CommunityCommentDto.ForDetail forDetailDto = comment.toForDetailDto();
            map.put(forDetailDto.getCommentSeq(), forDetailDto);
            if (comment.getParent() != null) {
                map.get(comment.getParent().getSeq()).getChildren().add(forDetailDto);
            } else {
                result.add(forDetailDto);
            }
        });
        return result;
    }
}
