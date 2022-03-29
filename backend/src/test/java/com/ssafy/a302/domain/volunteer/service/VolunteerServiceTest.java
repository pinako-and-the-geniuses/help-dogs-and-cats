package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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

    private MemberRequestDto.RegisterInfo registerInfo1;

    private VolunteerRequestDto.RegisterInfo registerInfo;

    private VolunteerRequestDto.StatusInfo statusInfo;

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


        registerInfo = VolunteerRequestDto.RegisterInfo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
//                .category("SHELTER")
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

        memberService.register(registerInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);
        assertThat(savedVolunteer.get().getSeq()).isNotNull();
        assertThat(savedVolunteer.get().getTitle()).isEqualTo(registerInfo.getTitle());
        assertThat(savedVolunteer.get().getContent()).isEqualTo(registerInfo.getContent());
//        assertThat(savedVolunteer.getCategory()).isEqualTo(Volunteer.Category.SHELTER);
        assertThat(savedVolunteer.get().getActivityArea()).isEqualTo(registerInfo.getActivityArea());

        assertThat(savedVolunteer.get().getAuthTime()).isEqualTo(registerInfo.getAuthTime());
        assertThat(savedVolunteer.get().getContact()).isEqualTo(registerInfo.getContact());
        assertThat(savedVolunteer.get().getEndDate()).isEqualTo(registerInfo.getEndDate());

        assertThat(savedVolunteer.get().getMinParticipantCount()).isEqualTo(registerInfo.getMinParticipantCount());
        assertThat(savedVolunteer.get().getMaxParticipantCount()).isEqualTo(registerInfo.getMaxParticipantCount());

    }

    @Test
    @DisplayName("봉사활동 삭제 - 성공")
    void deleteVolunteer() {

        memberService.register(registerInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);
        Volunteer deletedVolunteer = volunteerService.deleteVolunteer(savedVolunteer.get().getSeq(), savedMember.getSeq());

        assertThat(deletedVolunteer.isDeleted()).isTrue();

    }

    @Test
    @DisplayName("봉사활동 진행상태 수정 - 성공")
    void changeVolunteerStatus() {

        memberService.register(registerInfo1.toServiceDto());
        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        Long savedVolunteerSeq = volunteerService.register(registerInfo.toServiceDto(), savedMember.getSeq());
        Optional<Volunteer> savedVolunteer = volunteerRepository.findBySeq(savedVolunteerSeq);

        Optional<Volunteer> findVolunteer = volunteerRepository.findBySeq(savedVolunteer.get().getSeq());
        findVolunteer.get().changeVolunteerStatus(Volunteer.Status.valueOf("ONGOING"));

        assertThat(findVolunteer.get().getStatus()).isEqualTo(Volunteer.Status.valueOf("ONGOING"));

    }
}