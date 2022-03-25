package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.exception.DuplicateEmailException;
import com.ssafy.a302.domain.member.exception.DuplicateNicknameException;
import com.ssafy.a302.domain.member.exception.DuplicateTelException;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import com.ssafy.a302.global.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

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

    @Autowired
    private StringUtil stringUtil;

    private MemberRequestDto.RegisterInfo registerInfo1, registerInfo2;

    private MemberRequestDto.LoginInfo loginInfoByRegisterInfo1;

    private MemberRequestDto.ModifyInfo modifyInfo1;

    @Value("${path.images}")
    private String filePath;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
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
                .tel("010-0000-0002")
                .activityArea("서울시 도봉구")
                .build();

        loginInfoByRegisterInfo1 = MemberRequestDto.LoginInfo.builder()
                .email(registerInfo1.getEmail())
                .password(registerInfo1.getPassword())
                .build();

        modifyInfo1 = MemberRequestDto.ModifyInfo.builder()
                .password("modifyPass12#$")
                .nickname("modifyNickname1")
                .tel("010-9999-9999")
                .activityArea("서울시 도봉구")
                .build();
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void registerSuccess() {
        /**
         * registerInfo1 회원가입
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * 데이터베이스에 저장된 회원 데이터를 가져온다.
         */
        Member savedMember = memberService.getMemberByEmail(registerInfo1.getEmail());
        MemberDetail memberDetail = savedMember.getDetail();

        /**
         * 데이터 검증
         */
        assertThat(savedMember.getSeq()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(registerInfo1.getEmail());
        assertThat(passwordEncoder.matches(registerInfo1.getPassword(), savedMember.getPassword())).isTrue();
        assertThat(memberDetail.getNickname()).isEqualTo(registerInfo1.getNickname());
        assertThat(memberDetail.getTel()).isEqualTo(registerInfo1.getTel());
        assertThat(memberDetail.getActivityArea()).isEqualTo(registerInfo1.getActivityArea());
        assertThat(savedMember.getRole()).isEqualTo(Member.Role.MEMBER);
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
    @DisplayName("회원가입 - 예외 처리 : 핸드폰 번호 중복")
    void registerFailWhenTelDuplicate() {
        /**
         * 테스트용 데이터
         */
        String tel = "010-0001-0001";
        memberService.register(MemberRequestDto.RegisterInfo.builder()
                .email("test1@test.com")
                .password("password12#$")
                .nickname("good1")
                .tel(tel)
                .activityArea("서울시 강남구")
                .build().toServiceDto());

        /**
         * 중복된 이메일을 가진 데이터를 회원가입시킨다.
         */
        MemberRequestDto.RegisterInfo TelDuplicateRegisterInfo = MemberRequestDto.RegisterInfo.builder()
                .email("test2@tst.com")
                .password("password12#$")
                .nickname("good2")
                .tel(tel)
                .activityArea("서울시 강남구")
                .build();

        /**
         * 이메일이 중복되었으므로 예외가 발생해야 한다.
         */
        assertThatThrownBy(() -> memberService.register(TelDuplicateRegisterInfo.toServiceDto()))
                .isInstanceOf(DuplicateTelException.class)
                .hasMessage(Message.DUPLICATE_MEMBER_TEL);
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

    @Test
    @DisplayName("로그인 - 실패 : 회원 데이터 없음")
    void loginFailWhenMemberNull() {
        /**
         * 이메일이 데이터베이스에 없는 경우 예외가 발생한다.
         */
        assertThatThrownBy(() -> memberService.login(loginInfoByRegisterInfo1.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }

    @Test
    @DisplayName("회원정보 수정 - 성공")
    void memberInfoModifySuccess() {
        /**
         * registerInfo1 회원가입
         */
        MemberDto.Response savedMemberDto = memberService.register(registerInfo1.toServiceDto());

        /**
         * 회원가입된 정보와 변경할 정보가 다른 것을 확인
         */
        assertThat(passwordEncoder.matches(modifyInfo1.getPassword(), savedMemberDto.getPassword())).isFalse();
        assertThat(savedMemberDto.getNickname()).isNotEqualTo(modifyInfo1.getNickname());
        assertThat(savedMemberDto.getTel()).isNotEqualTo(modifyInfo1.getTel());
        assertThat(savedMemberDto.getActivityArea()).isNotEqualTo(modifyInfo1.getActivityArea());

        /**
         * 회원정보 수정
         */
        memberService.modify(savedMemberDto.getSeq(), modifyInfo1.toServiceDto());

        /**
         * DB에 저장된 회원 정보를 가져온다.
         */
        Member findMember = memberService.getMemberBySeq(savedMemberDto.getSeq());
        MemberDetail memberDetail = findMember.getDetail();

        /**
         * 데이터가 잘 변경되었는지 검증
         */
        assertThat(passwordEncoder.matches(modifyInfo1.getPassword(), findMember.getPassword())).isTrue();
        assertThat(memberDetail.getNickname()).isEqualTo(modifyInfo1.getNickname());
        assertThat(memberDetail.getTel()).isEqualTo(modifyInfo1.getTel());
        assertThat(memberDetail.getActivityArea()).isEqualTo(modifyInfo1.getActivityArea());
    }

    @Test
    @DisplayName("회원정보 수정 - 예외 처리 : 회원 데이터 없음")
    void memberInfoModifyFailWhenMemberNull() {
        assertThatThrownBy(() -> memberService.modify(0L, modifyInfo1.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }

    @Test
    @DisplayName("회원정보 수정 - 예외처리 : 중복된 닉네임")
    void memberInfoModifyFailWhenNicknameDuplicate() {
        /**
         * registerInfo1 데이터로 회원가입
         */
        Long savedMemberSeq = memberService.register(registerInfo1.toServiceDto()).getSeq();

        /**
         * 새로운 데이터를 회원가입 시키는데, 이 때 modifyInfo1의 닉네임을 사용한다.
         * 현재 registerInfo1의 닉네임은 modifyInfo1의 닉네임과 다르다.
         */
        memberService.register(MemberRequestDto.RegisterInfo.builder()
                .email("newEmail@test.com")
                .password("newPassword")
                .nickname(modifyInfo1.getNickname())
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build().toServiceDto());

        /**
         * registerInfo1 데이터로 회원가입한 정보를 modifyInfo1 데이터로 수정한다.
         * 이 때, 이미 위에서 modifyInfo1의 닉네임으로 가입하였기 때문에 예외가 발생한다.
         */
        assertThatThrownBy(() -> memberService.modify(savedMemberSeq, modifyInfo1.toServiceDto()))
                .isInstanceOf(DuplicateNicknameException.class)
                .hasMessage(Message.DUPLICATE_MEMBER_NICKNAME);
    }

    @Test
    @DisplayName("회원정보 수정 - 예외처리 : 중복된 핸드폰 번호")
    void memberInfoModifyFailWhenTelDuplicate() {
        /**
         * registerInfo1 데이터로 회원가입
         */
        Long savedMemberSeq = memberService.register(registerInfo1.toServiceDto()).getSeq();

        /**
         * 새로운 데이터를 회원가입 시키는데, 이 때 modifyInfo1의 핸드폰 번호를 사용한다.
         * 현재 registerInfo1의 핸드폰 번호는 modifyInfo1의 핸드폰 번호와 다르다.
         */
        memberService.register(MemberRequestDto.RegisterInfo.builder()
                .email("newEmail@test.com")
                .password("newPassword")
                .nickname("newNickname")
                .tel(modifyInfo1.getTel())
                .activityArea("서울시 강남구")
                .build().toServiceDto());

        /**
         * registerInfo1 데이터로 회원가입한 정보를 modifyInfo1 데이터로 수정한다.
         * 이 때, 이미 위에서 modifyInfo1의 핸드폰 번호로 가입하였기 때문에 예외가 발생한다.
         */
        assertThatThrownBy(() -> memberService.modify(savedMemberSeq, modifyInfo1.toServiceDto()))
                .isInstanceOf(DuplicateTelException.class)
                .hasMessage(Message.DUPLICATE_MEMBER_TEL);
    }

    @Test
    @DisplayName("회원정보 수정 - 예외처리 : 패스워드에 이메일이 포함된 경우")
    void memberInfoModifyFailWhenPasswordContainEmail() {
        /**
         * registerInfo1 데이터로 회원가입
         */
        Long savedMemberSeq = memberService.register(registerInfo1.toServiceDto()).getSeq();

        /**
         * 회원정보 수정용 데이터를 만든다. registerInfo1의 이메일을 가져와서 패스워드를 조합한다.
         */
        MemberRequestDto.ModifyInfo modifyInfo = MemberRequestDto.ModifyInfo.builder()
                .password(registerInfo1.getEmail().split("@")[0] + "12#$")
                .nickname(registerInfo1.getNickname())
                .tel(registerInfo1.getTel())
                .activityArea(registerInfo1.getActivityArea())
                .build();

        /**
         * 패스워드에 이메일이 포함되어 있으므로 예외가 발생해야 한다.
         * 이 때, 이미 위에서 modifyInfo1의 핸드폰 번호로 가입하였기 때문에 예외가 발생한다.
         */
        assertThatThrownBy(() -> memberService.modify(savedMemberSeq, modifyInfo.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Message.PASSWORD_CONTAIN_MEMBER_EMAIL);
    }

    @Test
    @DisplayName("회원정보 수정 - 예외처리 : 패스워드에 닉네임이 포함된 경우")
    void memberInfoModifyFailWhenPasswordContainNickname() {
        /**
         * registerInfo1 데이터로 회원가입
         */
        Long savedMemberSeq = memberService.register(registerInfo1.toServiceDto()).getSeq();

        /**
         * 회원정보 수정용 데이터를 만든다. registerInfo1의 이메일을 가져와서 패스워드를 조합한다.
         */
        MemberRequestDto.ModifyInfo modifyInfo = MemberRequestDto.ModifyInfo.builder()
                .password(registerInfo1.getNickname() + "12#$")
                .nickname(registerInfo1.getNickname())
                .tel(registerInfo1.getTel())
                .activityArea(registerInfo1.getActivityArea())
                .build();

        /**
         * 패스워드에 이메일이 포함되어 있으므로 예외가 발생해야 한다.
         * 이 때, 이미 위에서 modifyInfo1의 핸드폰 번호로 가입하였기 때문에 예외가 발생한다.
         */
        assertThatThrownBy(() -> memberService.modify(savedMemberSeq, modifyInfo.toServiceDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(Message.PASSWORD_CONTAIN_MEMBER_NICKNAME);
    }

    @Test
    @DisplayName("프로필 이미지 수정 - 성공, 프로필 이미지 삭제 - 성공")
    void profileImageModifySuccess() throws IOException {
        /**
         * 테스트용 데이터
         */
        String googleImageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_light_color_272x92dp.png";
        URL url = new URL(googleImageUrl);

        File baseProfileImageFile = new File("test.png");
        ImageIO.write(ImageIO.read(url), "png", baseProfileImageFile);

        MultipartFile testProfileImageFile = new MockMultipartFile(
                "profileImageFile",
                baseProfileImageFile.getName(),
                "image/png",
                new FileInputStream(baseProfileImageFile));

        assertThat(baseProfileImageFile.delete()).isTrue();

        /**
         * 회원가입
         */
        Long memberSeq = memberService.register(registerInfo1.toServiceDto()).getSeq();

        /**
         * 회원가입한 회원의 seq 로 프로필 이미지 변경
         */
        String profileImagePath = memberService.modifyProfileImage(memberSeq, testProfileImageFile);

        /**
         * 반환되는 이미지 경로에서 이미지 이름 추출
         */
        int indexOf = profileImagePath.lastIndexOf("/");
        String storeFilename = profileImagePath.substring(indexOf + 1);

        /**
         * 데이터베이스에 저장된 프로필 이미지 파일 이름과 반환 받은 이미지 이름이 같은지 검증한다.
         */
        String savedProfileImageFilename = memberService.getMemberBySeq(memberSeq).getDetail().getProfileImageFilename();
        assertThat(savedProfileImageFilename).isEqualTo(storeFilename);

        /**
         * 테스트를 마치면 테스트용 이미지 삭제
         */
        memberService.removeProfileImage(memberSeq);
        File file = new File(filePath + "profile" + File.separator + storeFilename);
        assertThat(file.exists()).isFalse();
    }

    @Test
    @DisplayName("핸드폰 번호로 마스킹된 이메일 조회 - 성공")
    void findMaskedEmailByTelSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        memberService.register(registerInfo1.toServiceDto());

        /**
         * 마스킹된 이메일 조회
         */
        String maskedEmail = memberService.findMaskedEmail(registerInfo1.getTel());

        /**
         * 데이터 검증
         */
        assertThat(maskedEmail).isNotNull();

        String[] emailInfo = registerInfo1.getEmail().split("@");
        String originMaskedEmail = stringUtil.mask(emailInfo[0]) + "@" + emailInfo[1];
        assertThat(maskedEmail).isEqualTo(originMaskedEmail);
    }
}