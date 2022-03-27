package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.controller.dto.CommunityCommentRequestDto;
import com.ssafy.a302.domain.community.controller.dto.CommunityRequestDto;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.entity.CommunityComment;
import com.ssafy.a302.domain.community.repository.CommunityCommentRepository;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommunityServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityCommentRepository communityCommentRepository;

    @Autowired
    private CommunityService communityService;

    private CommunityRequestDto.RegisterInfo registerInfo1;

    private Member member1, member2;

    private MemberDetail detail1, detail2;

    private Community community1, community2;

    private CommunityComment comment1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        communityRepository.deleteAll();
        em.flush();
        em.clear();

        registerInfo1 = CommunityRequestDto.RegisterInfo.builder()
                .title("title1")
                .content("content1")
                .category("rePort")
                .build();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("pass12#$")
                .role(Member.Role.MEMBER)
                .build();

        detail1 = MemberDetail.builder()
                .member(member1)
                .nickname("good1")
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build();

        member2 = Member.builder()
                .email("test2@test.com")
                .password("pass12#$")
                .role(Member.Role.MEMBER)
                .build();

        detail2 = MemberDetail.builder()
                .member(member2)
                .nickname("good2")
                .tel("010-0001-0002")
                .activityArea("서울시 강남구")
                .build();

        community1 = Community.builder()
                .title("입양 후기에요")
                .content("<p>입양했습니다.</p>")
                .category(Community.Category.REVIEW)
                .member(member1)
                .build();

        community2 = Community.builder()
                .title("유기동물 제보합니다")
                .content("<p>유기동물 제보해요</p>")
                .category(Community.Category.REPORT)
                .member(member2)
                .build();

        comment1 = CommunityComment.builder()
                .member(member1)
                .community(community1)
                .content("댓글1")
                .build();
    }

    @Test
    @DisplayName("커뮤니티 등록 - 성공")
    void registerCommunitySuccess() {
        Member savedMember = memberRepository.save(member1);

        /**
         * 커뮤니티 저장
         */
        Long savedCommunitySeq = communityService.register(registerInfo1.toServiceDto(), savedMember.getSeq());

        /**
         * 커뮤니티 조회
         */
        Community findCommunity = communityRepository.findBySeq(savedCommunitySeq)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(findCommunity).isNotNull();
        assertThat(findCommunity.getTitle()).isEqualTo(registerInfo1.getTitle());
        assertThat(findCommunity.getContent()).isEqualTo(registerInfo1.getContent());
        assertThat(findCommunity.getCategory()).isEqualTo(Community.Category.valueOf(registerInfo1.getCategory().toUpperCase()));
        assertThat(findCommunity.getViewCount()).isEqualTo(0L);
        assertThat(findCommunity.getMember().getSeq()).isEqualTo(savedMember.getSeq());
    }

    @Test
    @DisplayName("커뮤니티 페이지 조회 - 성공")
    void viewCommunityPageSuccess() {
        PageRequest pageable = PageRequest.of(1, 10);
        CommunityDto.CommunityListPage page0 = communityService.getPage(pageable, null, "", "");
        assertThat(page0.getTotalCount()).isEqualTo(0);
        assertThat(page0.getCurrentPageNumber()).isEqualTo(1);
        assertThat(page0.getTotalPageNumber()).isEqualTo(0);
        assertThat(page0.getCommunitiesForPage()).isNull();
        ;

        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        communityRepository.save(community1);
        communityRepository.save(community2);
        em.flush();
        em.clear();

        CommunityDto.CommunityListPage page1 = communityService.getPage(pageable, null, "", "");
        assertThat(page1.getTotalCount()).isEqualTo(2);
        assertThat(page1.getCurrentPageNumber()).isEqualTo(1);
        assertThat(page1.getTotalPageNumber()).isEqualTo(1);
        assertThat(page1.getCommunitiesForPage().size()).isEqualTo(2);

        List<CommunityDto.ForPage> communitiesForPage = page1.getCommunitiesForPage();
        CommunityDto.ForPage forPage1 = communitiesForPage.get(0);
        assertThat(forPage1.getTitle()).isEqualTo(community1.getTitle());
        assertThat(forPage1.getViewCount()).isEqualTo(0);
        assertThat(forPage1.getCategory()).isEqualTo(Community.Category.REVIEW);
        assertThat(forPage1.getMemberSeq()).isEqualTo(savedMember1.getSeq());
        assertThat(forPage1.getMemberNickname()).isEqualTo(savedMember1.getDetail().getNickname());

        CommunityDto.ForPage forPage2 = communitiesForPage.get(1);
        assertThat(forPage2.getTitle()).isEqualTo(community2.getTitle());
        assertThat(forPage2.getViewCount()).isEqualTo(0);
        assertThat(forPage2.getCategory()).isEqualTo(Community.Category.REPORT);
        assertThat(forPage2.getMemberSeq()).isEqualTo(savedMember2.getSeq());
        assertThat(forPage2.getMemberNickname()).isEqualTo(savedMember2.getDetail().getNickname());
    }

    @Test
    @DisplayName("커뮤니티 게시글 댓글 등록 - 성공")
    void registerCommunityCommentSuccess() {
        /**
         * 테스트 데이ㅓㅌ 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Community savedCommunity = communityRepository.save(community1);
        em.flush();
        em.clear();

        CommunityCommentRequestDto.RegisterInfo registerCommentInfo1 = CommunityCommentRequestDto.RegisterInfo.builder()
                .content("댓글 테스트")
                .parentSeq(null)
                .build();

        /**
         * 커뮤니티 게시글 댓글 저장
         */
        Long savedCommentSeq = communityService.registerComment(savedCommunity.getSeq(), registerCommentInfo1.toServiceDto(), savedMember2.getSeq());

        /**
         * 저장된 커뮤니티 게시글 댓글 가져오기
         */
        CommunityComment savedCommunityComment = communityCommentRepository.findBySeq(savedCommentSeq)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(savedCommunityComment).isNotNull();
        assertThat(savedCommunityComment.getContent()).isEqualTo(registerCommentInfo1.getContent());
        assertThat(savedCommunityComment.getParent()).isNull();
        assertThat(savedCommunityComment.getMember().getSeq()).isEqualTo(savedMember2.getSeq());
    }

    @Test
    @DisplayName("커뮤니티 게시글 댓글 삭제 - 성공")
    void removeCommunityCommentSuccess() {
        /**
         * 테스트 데이ㅓㅌ 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Community savedCommunity1 = communityRepository.save(community1);
        CommunityComment savedComment1 = communityCommentRepository.save(comment1);
        em.flush();
        em.clear();

        /**
         * 댓글 조회
         */
        CommunityComment findComment1 = communityCommentRepository.findBySeq(savedComment1.getSeq())
                .orElse(null);
        assertThat(findComment1).isNotNull();

        /**
         * 뎃글 삭제
         */
        communityService.removeComment(savedCommunity1.getSeq(), savedComment1.getSeq(), savedMember1.getSeq());

        CommunityComment findComment2 = communityCommentRepository.findBySeq(savedComment1.getSeq())
                .orElse(null);
        assertThat(findComment2).isNull();;
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제 - 성공")
    void removeCommunitySuccess() {
        /**
         * 테스트 데이ㅓㅌ 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Community savedCommunity1 = communityRepository.save(community1);
        em.flush();
        em.clear();

        /**
         * 삭제 전 데이터 검증
         */
        Community findCommunity = communityRepository.findBySeq(savedCommunity1.getSeq())
                .orElse(null);
        assertThat(findCommunity).isNotNull();
        assertThat(findCommunity.getMember().getSeq()).isEqualTo(savedMember1.getSeq());

        /**
         * 삭제 후 데이터 검증
         */
        communityService.remove(savedCommunity1.getSeq(), savedMember1.getSeq());
        Community removedCommunity = communityRepository.findBySeq(savedCommunity1.getSeq())
                .orElse(null);
        assertThat(removedCommunity).isNull();
    }
}