package com.ssafy.a302.domain.community.service;

import com.ssafy.a302.domain.community.controller.dto.CommunityRequestDto;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
    private CommunityService communityService;

    private CommunityRequestDto.RegisterInfo registerInfo1;

    private Member member1;

    private MemberDetail detail1;

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
}