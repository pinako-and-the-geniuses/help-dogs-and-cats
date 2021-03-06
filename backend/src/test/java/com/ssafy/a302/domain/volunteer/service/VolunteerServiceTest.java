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
                .activityArea("????????? ?????????")
                .build();

        MemberRegisterInfo2 = MemberRequestDto.RegisterInfo.builder()
                .email("test2@test.com")
                .password("pass12#$")
                .nickname("good2")
                .tel("010-0000-0002")
                .activityArea("????????? ?????????")
                .build();

        VolunteerRegisterInfo1 = VolunteerRequestDto.RegisterInfo.builder()
                .title("???????????????.")
                .content("???????????????.")
                .activityArea("????????? ?????????")
                .authTime("12h")
                .contact("www.dogsandcats.com")
                .endDate("2022-03-30")
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .build();
    }


    @Test
    @DisplayName("???????????? ?????? - ??????")
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
    @DisplayName("???????????? ?????? - ??????")
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
    @DisplayName("???????????? ???????????? ?????? - ??????")
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
    @DisplayName("???????????? ?????? ?????? - ??????")
    void requestVolunteerAuthSuccess() {
        /**
         * ????????? ????????? ??????
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("?????? ?????? ??????")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * ???????????? ?????? ??????
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * ???????????? ?????? ????????? ??????
         */
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedVolunteerAuthSeq)
                .orElse(null);

        /**
         * ????????? ??????
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
    @DisplayName("???????????? ?????? ?????? ?????? - ??????")
    void modifyVolunteerAuthSuccess() {
        /**
         * ????????? ????????? ??????
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("?????? ?????? ??????")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * ???????????? ?????? ??????
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * ???????????? ?????? ????????? ??????
         */
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedVolunteerAuthSeq)
                .orElse(null);

        /**
         * ?????? ??????
         */
        findVolunteerAuth.modifyStatusToReject();

        /**
         * ?????? ?????? ?????????
         */
        volunteerAuthModifyInfo1 = VolunteerAuthRequestDto.ModifyInfo.builder()
                .content("?????? ?????? ??????????????????.")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();
        Long savedModifiedVolunteerAuthSeq = volunteerService.modifyVolunteerAuth(savedMemberSeq1, savedVolunteerSeq, volunteerAuthModifyInfo1.toServiceDto());

        /**
         * ?????? ?????? ????????? ??????
         */
        VolunteerAuth findModifiedVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(savedModifiedVolunteerAuthSeq)
                .orElse(null);

        /**
         * ????????? ??????
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
    @DisplayName("???????????? ?????? ?????? - ??????")
    void getVolunteerAuthSuccess() {
        /**
         * ????????? ????????? ??????
         */
        Long savedMemberSeq1 = memberService.register(MemberRegisterInfo1.toServiceDto()).getSeq();
        Member findMember1 = memberService.getMemberBySeq(savedMemberSeq1);
        Long savedVolunteerSeq = volunteerService.register(VolunteerRegisterInfo1.toServiceDto(), findMember1.getSeq());
        volunteerAuthRequestInfo1 = VolunteerAuthRequestDto.RequestInfo.builder()
                .content("?????? ?????? ??????")
                .authenticatedParticipantSequences(List.of(findMember1.getSeq()))
                .build();

        /**
         * ???????????? ?????? ??????
         */
        Long savedVolunteerAuthSeq = volunteerService.requestVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq, volunteerAuthRequestInfo1.toServiceDto());

        /**
         * ???????????? ?????? ??????
         */
        VolunteerDto.VolunteerAuthDetail volunteerAuthDetailDto = volunteerService.getVolunteerAuth(findMember1.getSeq(), savedVolunteerSeq);

        /**
         * ????????? ??????
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
         * ????????? ???????????? ????????? ?????? ????????? ????????? ???????????? ??????.
         */
        assertThat(participant.isApprove()).isTrue();
    }
}