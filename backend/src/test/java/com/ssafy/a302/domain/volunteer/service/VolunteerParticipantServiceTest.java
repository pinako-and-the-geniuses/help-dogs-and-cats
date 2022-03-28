package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerParticipantRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VolunteerParticipantServiceTest {

    @Autowired
    private VolunteerParticipantRepository volunteerParticipantRepository;

    @Autowired
    private VolunteerParticipantService volunteerParticipantService;

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

    private MemberRequestDto.RegisterInfo registerInfo1, registerInfo2;

    private VolunteerRequestDto.RegisterInfo registerInfo;

    private VolunteerRequestDto.StatusInfo statusInfo;

    private VolunteerParticipantRequestDto.IsApproveInfo isApproveInfo;

    @BeforeEach
    void setUp() {
        volunteerRepository.deleteAll();
        memberRepository.deleteAll();
        em.flush();
        em.clear();

        registerInfo1 = MemberRequestDto.RegisterInfo.builder()
                .email("test1@test.com")
                .password("pass12#$")
                .nickname("good1")
                .tel("010-0000-0001")
                .activityArea("서울시 강남구")
                .build();

        registerInfo2 = MemberRequestDto.RegisterInfo.builder()
                .email("test2@test.com")
                .password("pass12#$")
                .nickname("good2")
                .tel("010-0000-0001")
                .activityArea("서울시 강남구")
                .build();


        registerInfo = VolunteerRequestDto.RegisterInfo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .category("SHELTER")
                .activityArea("서울시 강남구")
                .minParticipantCount(2)
                .maxParticipantCount(6)
                .build();
    }

    @Test
    @DisplayName("봉사활동 신청 - 성공")
    void applyVolunteer() {
//        memberService.register(registerInfo1.toServiceDto());
//        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
//        MemberDetail memberDetail = savedMember.getDetail();
//
//        VolunteerDto.Response savedVolunteer = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());
//
//        Optional<Volunteer> findVolunteer = volunteerRepository.findBySeq(savedVolunteer.getSeq());
//
//        VolunteerParticipantDto.Response findVolunteerParticipant = volunteerParticipantService.applyVolunteer(findVolunteer.get().getSeq(), savedMember.getSeq());
//
//        assertThatThrownBy(() -> findVolunteerParticipant.getMember().getSeq()).isEqualTo(savedMember.getSeq())
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage(ErrorMessage.INVALID_VOLUNTEER_PARTICIPANT);


    }

    @Test
    @DisplayName("봉사활동 신청 취소 - 성공")
    void cancelVolunteer() {
//        memberService.register(registerInfo1.toServiceDto());
//        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
//        MemberDetail memberDetail = savedMember.getDetail();
//
//        VolunteerDto.Response savedVolunteer = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());
//
//        Optional<Volunteer> findVolunteer = volunteerRepository.findBySeq(savedVolunteer.getSeq());
//
//        volunteerParticipantService.cancelVolunteer(findVolunteer.get().getSeq(), savedMember.getSeq());
//
//        Optional<VolunteerParticipant> findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(savedMember.getSeq(), findVolunteer.get().getSeq());
//
//        assertThat(findVolunteerParticipant).isEmpty();

    }

    @Test
    @DisplayName("봉사활동 참여자 목록 조회 - 성공")
    void getVolunteerParticipantList() {
        memberService.register(registerInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        VolunteerDto.Response savedVolunteer = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());

        List<VolunteerParticipantServiceImpl.SimpleVolunteerParticipantDto> volunteerList =  volunteerParticipantService.getVolunteerParticipantList(savedVolunteer.getSeq(), savedMember.getSeq());

        assertThat(volunteerList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("봉사활동 참여자 참석여부 조회 - 성공")
    void changeParticipantIsApprove() {
    }

    @Test
    void deleteVolunteerParticipant() {
    }
}