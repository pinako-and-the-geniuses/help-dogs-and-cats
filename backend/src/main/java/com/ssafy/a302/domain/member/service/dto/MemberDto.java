package com.ssafy.a302.domain.member.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Schema(name = "회원 DTO", description = "회원 DTO 입니다.")
@SuperBuilder
@Getter
@ToString(of = {"seq", "email", "password", "role", "nickname", "tel", "activityArea", "exp", "level", "profileImageFilename"})
public class MemberDto {

    @Schema(name = "seq", title = "회원 기본키", description = "회원이 가지고 있는 고유 식별키입니다.")
    private final Long seq;

    @Schema(name = "email", title = "이메일", description = "이메일입니다.")
    private final String email;

    @Schema(name = "password", title = "패스워드", description = "패스워드입니다.")
    private final String password;

    @Schema(name = "role", title = "권한", description = "권한입니다.")
    private final Member.Role role;

    @Schema(name = "nickname", title = "닉네임", description = "닉네임입니다.")
    private final String nickname;;

    @Schema(name = "tel", title = "핸드폰 번호", description = "핸드폰 번호입니다.")
    private final String tel;

    @Schema(name = "activityArea", title = "활동 지역", description = "활동 지역입니다.")
    private final String activityArea;

    @Schema(name = "exp", title = "경험치", description = "경험치입니다.")
    private final Integer exp;

    @Schema(name = "level", title = "레벨", description = "레벨입니다.")
    private final Integer level;

    @Schema(name = "profileImageFilename", title = "프로필 이미지 파일 이름", description = "프로필 이미지 파일 이름입니다.")
    private final String profileImageFilename;

    @QueryProjection
    public MemberDto(Long seq, String email, String password, Member.Role role, String nickname, String tel, String activityArea, Integer exp, String profileImageFilename) {
        this.seq = seq;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.tel = tel;
        this.activityArea = activityArea;
        this.exp = exp;
        this.level = this.exp / 100 + 1;
        this.profileImageFilename = profileImageFilename;
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

    @Schema(name = "회원 프로필 DTO", description = "회원 프로필 DTO 입니다.")
    @SuperBuilder
    @Getter
    @ToString(of = {"profileImageFilePath", "badgesForProfile"})
    public static class Profile extends MemberDto {

        @JsonIgnore
        private String password;

        @JsonIgnore
        private Member.Role role;

        @JsonIgnore
        private String profileImageFilename;

        @Schema(name = "profileImageFilePath", title = "프로필 이미지 파일 경로", description = "프로필 이미지 파일 경로입니다.")
        private final String profileImageFilePath;

        @Schema(name = "badgesForProfile", title = "뱃지 목록", description = "전체 뱃지 목록입니다. 보유한 뱃지의 경우 achieve 값이 true 입니다.")
        private final List<BadgeDto.ForProfile> badgesForProfile;
    }

    public Profile createProfile(List<BadgeDto.ForProfile> badgesForProfile) {
        return Profile.builder()
                .seq(this.seq)
                .email(this.email)
                .nickname(this.nickname)
                .tel(this.tel)
                .activityArea(this.activityArea)
                .exp(this.exp)
                .level(this.level)
                .profileImageFilePath(this.profileImageFilename == null ? null : "static/images/profile/" + this.profileImageFilename)
                .badgesForProfile(badgesForProfile)
                .build();
    }
}
