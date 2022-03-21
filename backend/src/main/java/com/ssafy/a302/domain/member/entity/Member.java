package com.ssafy.a302.domain.member.entity;

import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.badge.entity.MemberBadge;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.entity.CommunityLike;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.report.entity.CautionHistory;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
        name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "email", "password", "role", "isDeleted"})
public class Member extends BaseLastModifiedEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "member", fetch = EAGER, cascade = ALL)
    private MemberDetail detail;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<SupportHistory> supportHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Community> communities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<CommunityLike> communityLikes = new ArrayList<>();

    /**
     * 현재 내가 작성한 커뮤니티 댓글 보기 기능이 없어서 주석 처리하였음
     */
//    @OneToMany(mappedBy = "member", cascade = ALL)
//    private final List<CommunityComment> communityComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<MemberBadge> memberBadges = new ArrayList<>();

    @OneToMany(mappedBy = "respondent", cascade = ALL)
    private List<CautionHistory> cautionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Volunteer> volunteers = new ArrayList<>();

    /**
     * 현재 내가 작성한 봉사활동 댓글 보기 기능이 없어서 주석 처리하였음
     */
//    @OneToMany(mappedBy = "member", cascade = ALL)
//    private List<VolunteerComment> volunteerComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<VolunteerParticipant> volunteerParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<AdoptAuth> adoptAuths = new ArrayList<>();

    @Builder
    public Member(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = false;
    }

    public void createDetail(MemberDetail detail) {
        this.detail = detail;
    }

    public enum Role implements GrantedAuthority {

        MEMBER(ROLES.MEMBER, "멤버권한"),
        ADMIN(ROLES.ADMIN, "관리자권한");

        public static class ROLES {
            public static final String MEMBER = "ROLE_MEMBER";
            public static final String ADMIN = "ROLE_ADMIN";
        }

        private final String authority;

        private final String description;

        Role (String authority, String description) {
            this.authority = authority;
            this.description = description;
        }

        @Override
        public String getAuthority() {
            return authority;
        }

        public String getDescription() {
            return description;
        }
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public MemberDto.Response toResponseDto() {
        return MemberDto.Response.builder()
                .seq(seq)
                .email(email)
                .password(password)
                .role(role)
                .nickname(detail.getNickname())
                .tel(detail.getTel())
                .activityArea(detail.getActivityArea())
                .build();
    }

    public MemberDto.LoginResponse toLoginResponseDto() {
        return MemberDto.LoginResponse.builder()
                .seq(seq)
                .email(email)
                .role(role)
                .nickname(detail.getNickname())
                .build();
    }
}
