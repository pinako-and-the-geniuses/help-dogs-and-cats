package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.Community;
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
class CommunityRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommunityRepository communityRepository;

    private Community community1, community2;

    private Member member1, member2;

    private MemberDetail detail1, detail2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        communityRepository.deleteAll();
        em.flush();
        em.clear();

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
    }

    @Test
    @DisplayName("커뮤니티 게시글 개수 조회 - 성공")
    void findCountCommunitySuccess() {
        Integer count1 = communityRepository.countAllByCategoryAndSearchAndKeyword(null, "", "");
        Integer count2 = communityRepository.countAllByCategoryAndSearchAndKeyword(Community.Category.REVIEW, "", "");
        Integer count3 = communityRepository.countAllByCategoryAndSearchAndKeyword(Community.Category.REPORT, "", "");
        assertThat(count1).isEqualTo(0);
        assertThat(count2).isEqualTo(0);
        assertThat(count3).isEqualTo(0);

        /**
         * 테스트 데이터 세팅
         */
        memberRepository.save(member1);
        memberRepository.save(member2);
        communityRepository.save(community1);
        communityRepository.save(community2);
        em.flush();
        em.clear();

        Integer count4 = communityRepository.countAllByCategoryAndSearchAndKeyword(null, "", "");
        Integer count5 = communityRepository.countAllByCategoryAndSearchAndKeyword(Community.Category.REVIEW, "", "");
        Integer count6 = communityRepository.countAllByCategoryAndSearchAndKeyword(Community.Category.REPORT, "", "");
        assertThat(count4).isEqualTo(2);
        assertThat(count5).isEqualTo(1);
        assertThat(count6).isEqualTo(1);

        /**
         * title.contains("기")
         */
        Integer count7 = communityRepository.countAllByCategoryAndSearchAndKeyword(null, "", "기");
        assertThat(count7).isEqualTo(2);
    }

    @Test
    @DisplayName("커뮤니티 게시글 목록 조회 - 성공")
    void findCommunitiesSuccess() {
        PageRequest pageable = PageRequest.of(1, 10);
        List<CommunityDto.ForPage> page0 = communityRepository.findCommunitiesForPage(pageable, null, "", "")
                .orElse(null);
        assertThat(page0).isNull();
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

        /**
         * 데이터 검증
         */
        List<CommunityDto.ForPage> page1 = communityRepository.findCommunitiesForPage(pageable, Community.Category.REVIEW, "", "")
                .orElse(null);
        assertThat(page1).isNotNull();
        assertThat(page1.size()).isEqualTo(1);
        CommunityDto.ForPage savedCommunityForPage1 = page1.get(0);
        assertThat(savedCommunityForPage1.getTitle()).isEqualTo(community1.getTitle());
        assertThat(savedCommunityForPage1.getViewCount()).isEqualTo(0);
        assertThat(savedCommunityForPage1.getCategory()).isEqualTo(Community.Category.REVIEW);
        assertThat(savedCommunityForPage1.getMemberSeq()).isEqualTo(savedMember1.getSeq());
        assertThat(savedCommunityForPage1.getMemberNickname()).isEqualTo(savedMember1.getDetail().getNickname());

        List<CommunityDto.ForPage> page2 = communityRepository.findCommunitiesForPage(pageable, Community.Category.REPORT, "", "")
                .orElse(null);
        assertThat(page2).isNotNull();
        assertThat(page2.size()).isEqualTo(1);
        CommunityDto.ForPage savedCommunityForPage2 = page2.get(0);
        assertThat(savedCommunityForPage2.getTitle()).isEqualTo(community2.getTitle());
        assertThat(savedCommunityForPage2.getViewCount()).isEqualTo(0);
        assertThat(savedCommunityForPage2.getCategory()).isEqualTo(Community.Category.REPORT);
        assertThat(savedCommunityForPage2.getMemberSeq()).isEqualTo(savedMember2.getSeq());
        assertThat(savedCommunityForPage2.getMemberNickname()).isEqualTo(savedMember2.getDetail().getNickname());

        List<CommunityDto.ForPage> page3 = communityRepository.findCommunitiesForPage(pageable, null, "", "")
                .orElse(null);
        assertThat(page3).isNotNull();
        assertThat(page3.size()).isEqualTo(2);

        CommunityDto.ForPage savedCommunityForPage3 = page3.get(0);
        assertThat(savedCommunityForPage3.getTitle()).isEqualTo(community1.getTitle());
        assertThat(savedCommunityForPage3.getViewCount()).isEqualTo(0);
        assertThat(savedCommunityForPage3.getCategory()).isEqualTo(Community.Category.REVIEW);
        assertThat(savedCommunityForPage3.getMemberSeq()).isEqualTo(savedMember1.getSeq());
        assertThat(savedCommunityForPage3.getMemberNickname()).isEqualTo(savedMember1.getDetail().getNickname());

        CommunityDto.ForPage savedCommunityForPage4 = page3.get(1);
        assertThat(savedCommunityForPage4.getTitle()).isEqualTo(community2.getTitle());
        assertThat(savedCommunityForPage4.getViewCount()).isEqualTo(0);
        assertThat(savedCommunityForPage4.getCategory()).isEqualTo(Community.Category.REPORT);
        assertThat(savedCommunityForPage4.getMemberSeq()).isEqualTo(savedMember2.getSeq());
        assertThat(savedCommunityForPage4.getMemberNickname()).isEqualTo(savedMember2.getDetail().getNickname());
    }
}