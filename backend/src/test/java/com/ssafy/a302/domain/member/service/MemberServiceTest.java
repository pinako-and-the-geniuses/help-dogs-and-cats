package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.exception.DuplicateEmailException;
import com.ssafy.a302.domain.member.exception.DuplicateNicknameException;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.message.Message;
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

    @Test
    @DisplayName("회원가입 - 예외 처리 : 이메일 중복")
    void registerFailWhenEmailDuplicate() {
        /**
         * 테스트용 데이터
         */
        String email = "test1@test.com";
        memberService.register(MemberRequestDto.RegisterInfo.builder()
                .email(email)
                .password("password12#$")
                .nickname("good1")
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build().toServiceDto());

        /**
         * 중복된 이메일을 가진 데이터를 회원가입시킨다.
         */
        MemberRequestDto.RegisterInfo duplicateEmailRegisterInfo = MemberRequestDto.RegisterInfo.builder()
                .email(email)
                .password("password12#$")
                .nickname("good2")
                .tel("010-0001-0002")
                .activityArea("서울시 강남구")
                .build();

        /**
         * 이메일이 중복되었으므로 예외가 발생해야 한다.
         */
        assertThatThrownBy(() -> memberService.register(duplicateEmailRegisterInfo.toServiceDto()))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage(Message.DUPLICATE_MEMBER_EMAIL);
    }

    @Test
    @DisplayName("회원가입 - 예외 처리 : 닉네임 중복")
    void registerFailWhenNicknameDuplicate() {
        /**
         * 테스트용 데이터
         */
        String nickname = "good";
        memberService.register(MemberRequestDto.RegisterInfo.builder()
                .email("test1@test.com")
                .password("password12#$")
                .nickname(nickname)
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build().toServiceDto());

        /**
         * 중복된 이메일을 가진 데이터를 회원가입시킨다.
         */
        MemberRequestDto.RegisterInfo nicknameDuplicateRegisterInfo = MemberRequestDto.RegisterInfo.builder()
                .email("test2@tst.com")
                .password("password12#$")
                .nickname(nickname)
                .tel("010-0001-0002")
                .activityArea("서울시 강남구")
                .build();

        /**
         * 이메일이 중복되었으므로 예외가 발생해야 한다.
         */
        assertThatThrownBy(() -> memberService.register(nicknameDuplicateRegisterInfo.toServiceDto()))
                .isInstanceOf(DuplicateNicknameException.class)
                .hasMessage(Message.DUPLICATE_MEMBER_NICKNAME);
    }

    @Test
    @DisplayName("회원가입 - 예외 처리 : 패스워드에 이메일이 포함된 경우")
    void registerFailWhenPasswordContainEmail() {
        /**
         * 테스트용 데이터
         */
        String email = "test1@test.com";
        String password = "test12#$";
        MemberRequestDto.RegisterInfo registerInfo1 = MemberRequestDto.RegisterInfo.builder()
                .email(email)
                .password(password)
                .nickname("good")
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build();

        /**
         * 패스워드에 이메일이 포함되었으므로 예외가 발생해야 한다.
         */
        assertThatThrownBy(() -> memberService.register(registerInfo1.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Message.PASSWORD_CONTAIN_MEMBER_EMAIL);
    }

    @Test
    @DisplayName("회원가입 - 예외 처리 : 패스워드에 닉네임이 포함된 경우")
    void registerFailWhenPasswordContainNickname() {
        /**
         * 테스트용 데이터
         */
        String password = "test12#$";
        String nickname = "test";
        MemberRequestDto.RegisterInfo registerInfo1 = MemberRequestDto.RegisterInfo.builder()
                .email("hello@test.com")
                .password(password)
                .nickname(nickname)
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build();

        /**
         * 패스워드에 닉네임이 포함되었으므로 예외가 발생해야 한다.
         */
        assertThatThrownBy(() -> memberService.register(registerInfo1.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Message.PASSWORD_CONTAIN_MEMBER_NICKNAME);
    }
}