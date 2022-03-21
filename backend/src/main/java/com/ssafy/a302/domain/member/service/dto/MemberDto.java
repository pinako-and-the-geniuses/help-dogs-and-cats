package com.ssafy.a302.domain.member.service.dto;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import lombok.Builder;
import lombok.Getter;

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
}
