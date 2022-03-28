package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.entity.MemberBadge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.badge.repository.MemberBadgeRepository;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.constant.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfileServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MemberBadgeRepository memberBadgeRepository;

    @Autowired
    private ProfileService profileService;

    private Member member1;

    private MemberDetail detail1;

    private Badge badge1;

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

        badge1 = Badge.builder()
                .name("name1")
                .content("content1")
                .howToGet("howToGet1")
                .imageFilename("filename1")
                .build();
    }

    @Test
    @DisplayName("회원 프로필 조회 - 성공")
    void findMemberProfileSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        detail1.changeProfileImageFilename("image.png");
        Member savedMember = memberRepository.save(member1);
        Badge savedBadge = badgeRepository.save(badge1);
        MemberBadge savedMemberBadge = memberBadgeRepository.save(MemberBadge.builder()
                .member(savedMember)
                .badge(savedBadge)
                .build());
        em.flush();
        em.clear();


        /**
         * 프로필 조회
         */
        MemberDto.Profile memberProfileDto = profileService.getProfile(savedMember.getSeq());

        /**
         * 데이터 검증
         */
        MemberDetail detail = member1.getDetail();
        assertThat(memberProfileDto.getSeq()).isEqualTo(savedMember.getSeq());
        assertThat(memberProfileDto.getEmail()).isEqualTo(member1.getEmail());
        assertThat(memberProfileDto.getPassword()).isNull();
        assertThat(memberProfileDto.getRole()).isNull();
        assertThat(memberProfileDto.getNickname()).isEqualTo(detail.getNickname());
        assertThat(memberProfileDto.getTel()).isEqualTo(detail.getTel());
        assertThat(memberProfileDto.getActivityArea()).isEqualTo(detail.getActivityArea());
        assertThat(memberProfileDto.getExp()).isEqualTo(detail.getExp());
        assertThat(memberProfileDto.getLevel()).isEqualTo(detail.getExp() / 100 + 1);
        assertThat(memberProfileDto.getProfileImageFilename()).isNull();
        assertThat(memberProfileDto.getProfileImageFilePath()).isEqualTo(Path.PROFILE_IMAGE_ACCESS_PATH + "/" + detail.getProfileImageFilename());

        List<BadgeDto.ForProfile> badgesForProfile = memberProfileDto.getBadgesForProfile();
        for (BadgeDto.ForProfile badgeForProfile : badgesForProfile) {
            if (badgeForProfile.getSeq().equals(savedBadge.getSeq())) {
                assertThat(badgeForProfile.isAchieve()).isTrue();
                assertThat(badgeForProfile.getName()).isEqualTo(savedBadge.getName());
                assertThat(badgeForProfile.getContent()).isEqualTo(savedBadge.getContent());
                assertThat(badgeForProfile.getHowToGet()).isEqualTo(savedBadge.getHowToGet());
                assertThat(badgeForProfile.getImageFilename()).isNull();
                assertThat(badgeForProfile.getImageFilePath()).isEqualTo(Path.BADGE_IMAGE_ACCESS_PATH + "/" + savedBadge.getImageFilename());
            } else {
                assertThat(badgeForProfile.isAchieve()).isFalse();
            }
        }
    }
}