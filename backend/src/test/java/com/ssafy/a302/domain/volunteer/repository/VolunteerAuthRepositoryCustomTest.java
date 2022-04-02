package com.ssafy.a302.domain.volunteer.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class VolunteerAuthRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerAuthRepository volunteerAuthRepository;

    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    private Member member1, member2;

    private MemberDetail detail1, detail2;

    private Volunteer volunteer1, volunteer2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        volunteerRepository.deleteAll();
        volunteerAuthRepository.deleteAll();
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
    @DisplayName("봉사활동 인증 회원 목록 조회 - 성공")
    void findVolunteerParticipantsDtoByVolunteerSeqSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Volunteer savedVolunteer1 = volunteerRepository.save(volunteer1);
        volunteerParticipantRepository.save(VolunteerParticipant.builder()
                .volunteer(savedVolunteer1)
                .member(savedMember1)
                .build());
        volunteerParticipantRepository.save(VolunteerParticipant.builder()
                .volunteer(savedVolunteer1)
                .member(savedMember2)
                .build());
        volunteerAuthRepository.save(VolunteerAuth.builder()
                .volunteer(savedVolunteer1)
                .content("봉사활동 인증")
                .build());
        em.flush();
        em.clear();

        /**
         * 봉사활동 인증 회원 목록 조회
         */
        List<VolunteerDto.VolunteerAuthDetail.Participant> participants = volunteerAuthRepository.findVolunteerParticipantsDtoByVolunteerSeq(savedVolunteer1.getSeq())
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(participants).isNotNull();
        VolunteerDto.VolunteerAuthDetail.Participant participant1 = participants.get(0);
        assertThat(participant1.getMemberSeq()).isEqualTo(savedMember1.getSeq());
        assertThat(participant1.getNickname()).isEqualTo(savedMember1.getDetail().getNickname());
        assertThat(participant1.isApprove()).isFalse();

        VolunteerDto.VolunteerAuthDetail.Participant participant2 = participants.get(1);
        assertThat(participant2.getMemberSeq()).isEqualTo(savedMember2.getSeq());
        assertThat(participant2.getNickname()).isEqualTo(savedMember2.getDetail().getNickname());
        assertThat(participant2.isApprove()).isFalse();
    }
}