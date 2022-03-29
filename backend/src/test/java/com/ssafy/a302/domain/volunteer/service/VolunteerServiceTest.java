package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerAuthRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerAuthRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VolunteerServiceTest {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    @Autowired
    private VolunteerAuthRepository volunteerAuthRepository;

    private MemberRequestDto.RegisterInfo MemberRegisterInfo1, MemberRegisterInfo2;

    private VolunteerRequestDto.RegisterInfo VolunteerRegisterInfo1;

    private VolunteerRequestDto.StatusInfo statusInfo;

    private VolunteerAuthRequestDto.RequestInfo volunteerAuthRequestInfo1;

    private VolunteerAuthRequestDto.ModifyInfo volunteerAuthModifyInfo1;

    @BeforeEach
    void setUp() {
        volunteerRepository.deleteAll();
        memberRepository.deleteAll();
        em.flush();
        em.clear();

        MemberRegisterInfo1 = MemberRequestDto.RegisterInfo.builder()
                .email("test1@test.com")
                .password("pass12#$")
                .nickname("good1")
                .tel("010-0000-0001")
                .activityArea("서울시 강남구")
                .build();

        MemberRegisterInfo2 = MemberRequestDto.RegisterInfo.builder()
                .email("test2@test.com")
                .password("pass12#$")
                .nickname("good2")
                .tel("010-0000-0002")
                .activityArea("서울시 강남구")
                .build();

        VolunteerRegisterInfo1 = VolunteerRequestDto.RegisterInfo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .activityArea("서울시 강남구")
                .authTime("12h")
                .contact("www.dogsandcats.com")
                .endDate("2022-03-30")
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .build();
    }


    @Test
    @DisplayName("봉사활동 신청 - 성공")
    void register() {

        memberService.register(MemberRegisterInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(MemberRegisterInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);
        assertThat(savedVolunteer.get().getSeq()).isNotNull();
        assertThat(savedVolunteer.get().getTitle()).isEqualTo(VolunteerRegisterInfo1.getTitle());
        assertThat(savedVolunteer.get().getContent()).isEqualTo(VolunteerRegisterInfo1.getContent());
//        assertThat(savedVolunteer.getCategory()).isEqualTo(Volunteer.Category.SHELTER);
        assertThat(savedVolunteer.get().getActivityArea()).isEqualTo(VolunteerRegisterInfo1.getActivityArea());

        assertThat(savedVolunteer.get().getAuthTime()).isEqualTo(VolunteerRegisterInfo1.getAuthTime());
        assertThat(savedVolunteer.get().getContact()).isEqualTo(VolunteerRegisterInfo1.getContact());
        assertThat(savedVolunteer.get().getEndDate()).isEqualTo(VolunteerRegisterInfo1.getEndDate());

        assertThat(savedVolunteer.get().getMinParticipantCount()).isEqualTo(VolunteerRegisterInfo1.getMinParticipantCount());
        assertThat(savedVolunteer.get().getMaxParticipantCount()).isEqualTo(VolunteerRegisterInfo1.getMaxParticipantCount());

    }

    @Test
    @DisplayName("봉사활동 삭제 - 성공")
    void deleteVolunteer() {

        memberService.register(MemberRegisterInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(MemberRegisterInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);
        volunteerService.remove(savedVolunteer.get().getSeq(), savedMember.getSeq());
        Volunteer removedVolunteer = volunteerRepository.findBySeq(savedVolunteer.get().getSeq())
                        .orElse(null);
        assertThat(removedVolunteer).isNull();

    }

    @Test
    @DisplayName("봉사활동 진행상태 수정 - 성공")
    void changeVolunteerStatus() {

        memberService.register(MemberRegisterInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(MemberRegisterInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);

        Optional<Volunteer> findVolunteer = volunteerRepository.findBySeq(savedVolunteer.get().getSeq());
        findVolunteer.get().changeVolunteerStatus(Volunteer.Status.valueOf("ONGOING"));

        assertThat(findVolunteer.get().getStatus()).isEqualTo(Volunteer.Status.valueOf("ONGOING"));

    }

    @Test
    @DisplayName("봉사활동 인증 요청 - 성공")
    void requestVolunteerAuthSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("봉사 인증 신청")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * 봉사활동 인증 요청
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * 봉사활동 인증 엔티티 조회
         */
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedVolunteerAuthSeq)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(findVolunteerAuth).isNotNull();
        assertThat(findVolunteerAuth.getContent()).isEqualTo(volunteerAuthRequestInfo1.getContent());

        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(savedVolunteerSeq)
                .orElse(null);
        assertThat(volunteerParticipants).isNotNull();
        assertThat(volunteerParticipants.size()).isEqualTo(1);
        VolunteerParticipant volunteerParticipant = volunteerParticipants.get(0);
        assertThat(volunteerParticipant.getApprove()).isTrue();
    }

    @Test
    @DisplayName("봉사활동 인증 수정 요청 - 성공")
    void modifyVolunteerAuthSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("봉사 인증 신청")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * 봉사활동 인증 요청
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * 봉사활동 인증 엔티티 조회
         */
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedVolunteerAuthSeq)
                .orElse(null);

        /**
         * 반려 처리
         */
        findVolunteerAuth.modifyStatusToReject();

        /**
         * 봉사 인증 재요청
         */
        volunteerAuthModifyInfo1 = VolunteerAuthRequestDto.ModifyInfo.builder()
                .content("봉사 인증 재신청합니다.")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();
        Long savedModifiedVolunteerAuthSeq = volunteerService.modifyVolunteerAuth(savedMemberSeq1, savedVolunteerSeq, volunteerAuthModifyInfo1.toServiceDto());

        /**
         * 봉사 인증 엔티티 조회
         */
        VolunteerAuth findModifiedVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedModifiedVolunteerAuthSeq)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(findModifiedVolunteerAuth).isNotNull();
        assertThat(findModifiedVolunteerAuth.getContent()).isEqualTo(volunteerAuthModifyInfo1.getContent());

        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(savedVolunteerSeq)
                .orElse(null);
        assertThat(volunteerParticipants).isNotNull();
        assertThat(volunteerParticipants.size()).isEqualTo(1);
        VolunteerParticipant volunteerParticipant = volunteerParticipants.get(0);
        assertThat(volunteerParticipant.getApprove()).isTrue();
    }

    @Test
    @DisplayName("봉사활동 인증 조회 - 성공")
    void getVolunteerAuthSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("봉사 인증 신청")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * 봉사활동 인증 요청
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * 봉사활동 인증 조회
         */
        VolunteerDto.VolunteerAuthDetail volunteerAuthDetailDto = volunteerService.getVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq);

        /**
         * 데이터 검증
         */
        assertThat(volunteerAuthDetailDto.getContent()).isEqualTo(volunteerAuthRequestInfo1.getContent());
        assertThat(volunteerAuthDetailDto.getVolunteerSeq()).isEqualTo(savedVolunteerSeq);

        List<VolunteerDto.VolunteerAuthDetail.Participant> participants = volunteerAuthDetailDto.getParticipants();
        assertThat(participants).isNotNull();
        assertThat(participants.size()).isEqualTo(1);

        VolunteerDto.VolunteerAuthDetail.Participant participant = participants.get(0);
        assertThat(participant.getMemberSeq()).isEqualTo(findMember1.getSeq());
        assertThat(participant.getNickname()).isEqualTo(findMember1.getDetail().getNickname());
        /**
         * 서비스 메서드로 등록할 경우 모집한 사람은 승인되어 있다.
         */
        assertThat(participant.isApprove()).isTrue();
    }
}