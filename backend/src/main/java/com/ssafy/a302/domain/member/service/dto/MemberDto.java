package com.ssafy.a302.domain.member.service.dto;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class MemberDto {

    private final String email;

    private final String password;

    private final String nickname;

    private final String tel;

    private final String activityArea;

    @Builder
    public MemberDto(String email, String password, String nickname, String tel, String activityArea) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.tel = tel;
        this.activityArea = activityArea;
    }

    @Builder
    public MemberDto(String email, String password) {
        this(email, password, null, null, null);
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }

    public MemberDetail toDetailEntity(Member member) {
        return MemberDetail.builder()
                .member(member)
                .nickname(nickname)
                .tel(tel)
                .activityArea(activityArea)
                .build();
    }

    @Getter
    @ToString(of = {"seq", "email", "password", "role", "nickname", "tel", "activityArea"})
    public static class Response {

        @Schema(name = "seq", title = "회원 기본키", description = "회원이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "email", title = "이메일", description = "이메일입니다.")
        private final String email;

        @Schema(name = "password", title = "패스워드", description = "암호화된 패스워드입니다.")
        private final String password;

        @Schema(name = "role", title = "권한", description = "회원이 가지고 있는 권한입니다.")
        private final Member.Role role;

        @Schema(name = "nickname", title = "닉네임", description = "닉네임입니다.")
        private final String nickname;

        @Schema(name = "tel", title = "핸드폰 번호", description = "핸드폰 번호입니다.")
        private final String tel;

        @Schema(name = "activityArea", title = "활동 지역", description = "활동 지역입니다.")
        private final String activityArea;

        @Builder
        public Response(Long seq, String email, String password, Member.Role role, String nickname, String tel, String activityArea) {
            this.seq = seq;
            this.email = email;
            this.password = password;
            this.role = role;
            this.nickname = nickname;
            this.tel = tel;
            this.activityArea = activityArea;
        }
    }

    @Getter
    @ToString(of = {"seq", "email", "role", "nickname"})
    public static class LoginResponse {

        @Schema(name = "seq", title = "회원 기본키", description = "회원이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "email", title = "이메일", description = "이메일입니다.")
        private final String email;

        @Schema(name = "role", title = "권한", description = "회원이 가지고 있는 권한입니다.")
        private final Member.Role role;

        @Schema(name = "nickname", title = "닉네임", description = "닉네임입니다.")
        private final String nickname;

        @Builder
        public LoginResponse(Long seq, String email, String nickname, Member.Role role) {
            this.seq = seq;
            this.email = email;
            this.nickname = nickname;
            this.role = role;
        }
    }
}
