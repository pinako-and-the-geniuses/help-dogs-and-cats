package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MemberRequestDto.RegisterInfo registerInfo1, registerInfo2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

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
                .tel("010-0000-0002")
                .activityArea("서울시 도봉구")
                .build();
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void registerSuccess() {
        /**
         * registerInfo1 회원가입
         */
        MemberDto.Response responseDto = memberService.register(registerInfo1.toServiceDto());

        /**
         * 데이터 검증
         */
        assertThat(responseDto.getSeq()).isNotNull();
        assertThat(responseDto.getEmail()).isEqualTo(registerInfo1.getEmail());
        assertThat(passwordEncoder.matches(registerInfo1.getPassword(), responseDto.getPassword())).isTrue();
        assertThat(responseDto.getNickname()).isEqualTo(registerInfo1.getNickname());
        assertThat(responseDto.getTel()).isEqualTo(registerInfo1.getTel());
        assertThat(responseDto.getActivityArea()).isEqualTo(registerInfo1.getActivityArea());
        assertThat(responseDto.getRole()).isEqualTo(Member.Role.MEMBER);
    }
}