package com.ssafy.a302.domain.badge.service;

import com.ssafy.a302.domain.badge.constant.BadgeName;
import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.MemberService;
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
class BadgeServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private BadgeService badgeService;

    private Member member1;

    private MemberDetail detail1;

    private Badge welcomeBadge;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        badgeRepository.deleteAll();
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

        welcomeBadge = Badge.builder()
                .name(BadgeName.WELCOME_BADGE)
                .content("content1")
                .howToGet("howToGet1")
                .imageFilename("imageFilename1")
                .build();
    }

    @Test
    @DisplayName("회원가입 뱃지 자격 조회 - 성공")
    void isQualifiedWelcomeBadgeSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Badge savedWelcomeBadge = badgeRepository.save(welcomeBadge);

        /**
         * 가입 환영 뱃지 자격 조회
         */
        boolean exists1 = badgeService.isQualifiedWelcomeBadge(savedMember1.getSeq());

        /**
         * 데이터 검증
         */
        assertThat(exists1).isTrue();

        /**
         * 가입 환영 뱃지 승인
         */
        badgeService.approveWelcomeBadge(savedMember1.getSeq());

        boolean exists2 = badgeService.isQualifiedWelcomeBadge(savedMember1.getSeq());
        assertThat(exists2).isFalse();
    }
}