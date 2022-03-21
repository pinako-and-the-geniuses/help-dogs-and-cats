package com.ssafy.a302.domain.member.controller.dto;

import com.ssafy.a302.domain.member.service.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Schema(name = "회원 요청 DTO", description = "회원 API 호출 시 사용되는 요청 DTO 입니다.")
@Getter
public class MemberRequestDto {

    @Schema(name = "회원가입 요청 DTO", description = "회원가입 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"email", "password", "nickname", "tel", "activityArea"})
    public static class RegisterInfo {

        @Schema(name = "email", title = "이메일", description = "이메일입니다.", example = "good@good.com", required = true)
        @Pattern(message = "{pattern.member.email}", regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$")
        private String email;

        @Schema(name = "password", title = "패스워드", description = "패스워드입니다.", example = "test12#$", minLength = 8, maxLength = 20, required = true)
        @Pattern(message = "{pattern.member.password}", regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$")
        private String password;

        @Schema(name = "nickname", title = "닉네임", description = "닉네임입니다.", example = "good", minLength = 4, maxLength = 10, required = true)
        @Pattern(message = "{pattern.member.nickname}", regexp = "^[0-9|a-z|가-힣|\\s]{4,10}$")
        private String nickname;

        @Schema(name = "tel", title = "핸드폰 번호", description = "핸드폰 번호입니다.", example = "010-1234-5678", required = true)
        @Pattern(message = "{pattern.member.tel}", regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{3,4}$")
        private String tel;

        @Schema(name = "activityArea", title = "활동 지역", description = "활동 지역입니다.", example = "서울시 강남구", required = true)
        private String activityArea;

        @Builder
        public RegisterInfo(String email, String password, String nickname, String tel, String activityArea) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.tel = tel;
            this.activityArea = activityArea;
        }

        public MemberDto toServiceDto() {
            return MemberDto.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .tel(tel)
                    .activityArea(activityArea)
                    .build();
        }
    }

    @Schema(name = "로그인 요청 DTO", description = "로그인 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"email", "password"})
    public static class LoginInfo {

        @Schema(name = "email", title = "이메일", description = "이메일입니다.", example = "good@good.com", required = true)
        @Pattern(message = "{pattern.member.email}", regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$")
        private String email;

        @Schema(name = "password", title = "패스워드", description = "패스워드입니다.", example = "test12#$", minLength = 8, maxLength = 20, required = true)
        @Pattern(message = "{pattern.member.password}", regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$")
        private String password;

        @Builder
        public LoginInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public MemberDto toServiceDto() {
            return MemberDto.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}
