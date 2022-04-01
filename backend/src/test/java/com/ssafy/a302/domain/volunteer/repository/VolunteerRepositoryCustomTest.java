package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
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
class VolunteerRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    private Member member1, member2;

    private MemberDetail detail1, detail2;

    private Volunteer volunteer1, volunteer2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        volunteerRepository.deleteAll();
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

        volunteer1 = Volunteer.builder()
                .title("봉사활동 제목1")
                .contact("봉사활동 본문1")
                .activityArea("봉사활동 활동지역1")
                .authTime("인증시간1")
                .member(member1)
                .endDate(LocalDate.now())
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .content("contact1")
                .build();

        volunteer2 = Volunteer.builder()
                .title("봉사활동 제목2")
                .contact("봉사활동 본문2")
                .activityArea("봉사활동 활동지역2")
                .authTime("인증시간2")
                .member(member2)
                .endDate(LocalDate.now())
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .content("contact2")
                .build();
    }

    @Test
    @DisplayName("봉사활동 목록 조회 - 성공")
    void findVolunteersForProfile() {
        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        memberRepository.save(member2);
        Volunteer savedVolunteer1 = volunteerRepository.save(volunteer1);
        volunteerParticipantRepository.save(VolunteerParticipant.builder()
                .volunteer(savedVolunteer1)
                .member(savedMember1)
                .build());
        em.flush();
        em.clear();

        PageRequest pageable = PageRequest.of(1, 10);

        /**
         * savedMember1 봉사활동 목록 조회
         */
        List<ProfileDto.Volunteer> volunteersForProfile1 = volunteerRepository.findVolunteersForProfile(savedMember1.getSeq(), pageable)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(volunteersForProfile1).isNotNull();
        assertThat(volunteersForProfile1.size()).isEqualTo(1);
        ProfileDto.Volunteer volunteerForProfile1 = volunteersForProfile1.get(0);
        assertThat(volunteerForProfile1.getVolunteerSeq()).isEqualTo(savedVolunteer1.getSeq());

        Volunteer savedVolunteer2 = volunteerRepository.save(volunteer2);

        /**
         * 봉사활동 참여 신청
         */
        volunteerParticipantRepository.save(VolunteerParticipant.builder()
                .member(member1)
                .volunteer(savedVolunteer2)
                .build());

        List<ProfileDto.Volunteer> volunteersForProfile2 = volunteerRepository.findVolunteersForProfile(savedMember1.getSeq(), pageable)
                .orElse(null);
        assertThat(volunteersForProfile2.size()).isEqualTo(2);
    }
}