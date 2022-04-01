package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.entity.MemberBadge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.badge.repository.MemberBadgeRepository;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.global.constant.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
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

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private AdoptAuthRepository adoptAuthRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    private Member member1;

    private MemberDetail detail1;

    private Badge badge1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        badgeRepository.deleteAll();
        adoptAuthRepository.deleteAll();
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

    @Test
    @DisplayName("프로필 커뮤니티 이력 조회 - 성공")
    void findProfileCommunityPageSuccess() {
        Member savedMember1 = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * 데이터가 없을 떄
         */
        PageRequest pageable = PageRequest.of(1, 10);
        ProfileDto.CommunityPage communityPage0 = profileService.getCommunities(savedMember1.getSeq(), pageable);
        assertThat(communityPage0.getTotalCount()).isEqualTo(0);
        assertThat(communityPage0.getCommunities()).isNull();

        /**
         * 데이터 추가
         */
        Community savedCommunity1 = communityRepository.save(Community.builder()
                .member(savedMember1)
                .title("커뮤니티 글1")
                .content("커뮤니티 본문1")
                .category(Community.Category.REVIEW)
                .build());
        em.flush();
        em.clear();

        /**
         * 커뮤니티 페이지 데이터 조회
         */
        ProfileDto.CommunityPage communityPage1 = profileService.getCommunities(savedMember1.getSeq(), pageable);

        /**
         * 데이터 검증
         */
        assertThat(communityPage1.getTotalCount()).isEqualTo(1);
        assertThat(communityPage1.getTotalPageNumber()).isEqualTo(1);

        List<ProfileDto.Community> communities = communityPage1.getCommunities();
        ProfileDto.Community findCommunityForProfile = communities.get(0);
        assertThat(communities.size()).isEqualTo(1);
        assertThat(findCommunityForProfile.getSeq()).isEqualTo(savedCommunity1.getSeq());
        assertThat(findCommunityForProfile.getTitle()).isEqualTo(savedCommunity1.getTitle());
        assertThat(findCommunityForProfile.getCategory()).isEqualTo(savedCommunity1.getCategory());
    }

    @Test
    @DisplayName("프로필 입양 이력 조회 - 성공")
    void findProfileAdoptPageSuccess() {
        Member savedMember1 = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * 데이터가 없을 떄
         */
        PageRequest pageable = PageRequest.of(1, 10);
        ProfileDto.AdoptPage adoptPage0 = profileService.getAdopts(savedMember1.getSeq(), pageable);
        assertThat(adoptPage0.getTotalCount()).isEqualTo(0);
        assertThat(adoptPage0.getAdopts()).isNull();

        /**
         * 데이터 추가
         */
        AdoptAuth savedAdoptAuth = adoptAuthRepository.save(AdoptAuth.builder()
                .member(savedMember1)
                .title("입양 인증 신청합니다.")
                .content("얼마전에 입양했어요.")
                .build());
        em.flush();
        em.clear();

        /**
         * 입양 이력 페이지 데이터 조회
         */
        ProfileDto.AdoptPage adoptPage1 = profileService.getAdopts(savedMember1.getSeq(), pageable);

        /**
         * 데이터 검증
         */
        assertThat(adoptPage1.getTotalCount()).isEqualTo(1);
        assertThat(adoptPage1.getTotalPageNumber()).isEqualTo(1);

        List<ProfileDto.Adopt> adopts = adoptPage1.getAdopts();
        ProfileDto.Adopt findAdoptForProfile = adopts.get(0);
        assertThat(adopts.size()).isEqualTo(1);
        assertThat(findAdoptForProfile.getSeq()).isEqualTo(savedAdoptAuth.getSeq());
        assertThat(findAdoptForProfile.getTitle()).isEqualTo(savedAdoptAuth.getTitle());
        assertThat(findAdoptForProfile.getStatus()).isEqualTo(savedAdoptAuth.getStatus());
    }

    @Test
    @DisplayName("프로필 봉사활동 이력 조회 - 성공")
    void getVolunteersSuccess() {
        Member savedMember1 = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * 데이터가 없을 떄
         */
        PageRequest pageable = PageRequest.of(1, 10);
        ProfileDto.VolunteerPage volunteerPage0 = profileService.getVolunteers(savedMember1.getSeq(), pageable);
        assertThat(volunteerPage0.getTotalCount()).isEqualTo(0);
        assertThat(volunteerPage0.getVolunteers()).isNull();

        /**
         * 봉사활동 등록
         */
        Volunteer savedVolunteer1 = volunteerRepository.save(Volunteer.builder()
                .title("봉사활동 제목1")
                .contact("봉사활동 본문1")
                .activityArea("봉사활동 활동지역1")
                .authTime("인증시간1")
                .member(member1)
                .endDate(LocalDate.now())
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .content("contact1")
                .build());
        volunteerParticipantRepository.save(VolunteerParticipant.builder()
                .volunteer(savedVolunteer1)
                .member(savedMember1)
                .build());

        /**
         * 봉사활동 이력 조회
         */
        ProfileDto.VolunteerPage volunteerPage1 = profileService.getVolunteers(savedMember1.getSeq(), pageable);

        /**
         * 데이터 검증
         */
        assertThat(volunteerPage1.getTotalCount()).isEqualTo(1);
        assertThat(volunteerPage1.getVolunteers()).isNotNull();
        List<ProfileDto.Volunteer> volunteersForProfile = volunteerPage1.getVolunteers();
        ProfileDto.Volunteer volunteerForProfile = volunteersForProfile.get(0);
        assertThat(volunteerForProfile.getVolunteerSeq()).isEqualTo(savedVolunteer1.getSeq());
        assertThat(volunteerForProfile.getTitle()).isEqualTo(savedVolunteer1.getTitle());
        assertThat(volunteerForProfile.getMemberSeq()).isEqualTo(savedMember1.getSeq());
    }
}