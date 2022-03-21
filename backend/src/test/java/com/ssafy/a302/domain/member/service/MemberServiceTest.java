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

    private MemberRequestDto.LoginInfo loginInfoByRegisterInfo1;

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

        loginInfoByRegisterInfo1 = MemberRequestDto.LoginInfo.builder()
                .email(registerInfo1.getEmail())
                .password(registerInfo1.getPassword())
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

    @Test
    @DisplayName("이메일 중복 확인 - 중복인 경우")
    void emailDuplicateCheckYes() {
        /**
         * 테스트용 데이터
         */
        String email1 = registerInfo1.getEmail();
        String email2 = registerInfo2.getEmail();

        /**
         * 회원가입 전이므로 모두 false 가 반환된다.
         */
        boolean existsEmailFalse1 = memberService.isExistsEmail(email1);
        boolean existsEmailFalse2 = memberService.isExistsEmail(email2);
        assertThat(existsEmailFalse1).isFalse();
        assertThat(existsEmailFalse2).isFalse();

        /**
         * registerInfo1 데이터만 회원가입 한다.
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * registerInfo1 데이터로 회원가입했으므로
         * AfterSaveExistsEmailTrue 에 true 가 저장되어야 한다.
         * AfterSaveExistsEmailFalse 에 false 가 저장되어야 한다.
         */
        boolean AfterSaveExistsEmailTrue = memberService.isExistsEmail(email1);
        boolean AfterSaveExistsEmailFalse = memberService.isExistsEmail(email2);
        assertThat(AfterSaveExistsEmailTrue).isTrue();
        assertThat(AfterSaveExistsEmailFalse).isFalse();
    }

    @Test
    @DisplayName("이메일 중복 확인 - 중복이 아닌 경우")
    void emailDuplicateCheckNo() {
        // 테스트용 데이터
        String email = registerInfo1.getEmail();

        /**
         * 이메일 존재 유무 메서드 호출
         * 현재 데이터베이스에는 아무 데이터도 저장되지 않았다.
         */
        boolean existsEmailFalse = memberService.isExistsEmail(email);

        /**
         * 데이터베이스에 이메일이 없으면 메서드 반환 값이 false 이다.
         */
        assertThat(existsEmailFalse).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 중복인 경우")
    void nicknameDuplicateCheckYes() {
        /**
         * 테스트용 데이터
         */
        String nickname1 = registerInfo1.getNickname();
        String nickname2 = registerInfo2.getNickname();

        /**
         * 회원가입 전이므로 모두 false 가 반환되어야 한다.
         */
        boolean existsNicknameFalse1 = memberService.isExistsNickname(nickname1);
        boolean existsNicknameFalse2 = memberService.isExistsNickname(nickname2);
        assertThat(existsNicknameFalse1).isFalse();
        assertThat(existsNicknameFalse2).isFalse();

        /**
         * registerInfo1 데이터만 회원가입 한다.
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * registerInfo1 데이터로 회원가입했으므로
         * AfterSaveExistsNicknameTrue 에 true 가 저장되어야 한다.
         * AfterSaveExistsNicknameFalse 에 false 가 저장되어야 한다.
         */
        boolean AfterSaveExistsNicknameTrue = memberService.isExistsNickname(nickname1);
        boolean AfterSaveExistsNicknameFalse = memberService.isExistsNickname(nickname2);
        assertThat(AfterSaveExistsNicknameTrue).isTrue();
        assertThat(AfterSaveExistsNicknameFalse).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 중복이 아닌 경우")
    void nicknameDuplicateCheckNo() {
        /**
         * 테스트용 데이터
         */
        String nickname = "non-exists";

        /**
         * 닉네임 존재 유무 메서드 호출
         * 현재 데이터베이스에는 아무 데이터도 저장되지 않았다.
         */
        boolean existsNicknameFalse = memberService.isExistsNickname(nickname);

        /**
         * 데이터베이스에 닉네임이 없으면 메서드 반환 값이 false 이다.
         */
        assertThat(existsNicknameFalse).isFalse();
    }

    @Test
    @DisplayName("로그인 - 성공")
    void loginSuccess() {
        /**
         * 회원가입
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * 로그인 시도
         */
        boolean loginTrue = memberService.login(loginInfoByRegisterInfo1.toServiceDto());

        /**
         * 로그인 성공
         */
        assertThat(loginTrue).isTrue();
    }

    @Test
    @DisplayName("로그인 - 실패 : 패스워드 오입")
    void loginFailWhenInvalidPassword() {
        /**
         * 회원가입
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * 패스워드를 잘못 입력한 로그인 DTO 생성
         */
        MemberRequestDto.LoginInfo invalidLoginInfo = MemberRequestDto.LoginInfo.builder()
                .email(registerInfo1.getEmail())
                .password("invalidPassword")
                .build();

        /**
         * 로그인 시도
         */
        boolean loginFalse = memberService.login(invalidLoginInfo.toServiceDto());

        /**
         * 로그인 실패
         */
        assertThat(loginFalse).isFalse();
    }
}