package com.ssafy.a302.domain.member.entity;

import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "member", fetch = EAGER, cascade = ALL)
    private MemberDetail detail;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    public Member(String email, String password, Role role, MemberDetail detail) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.detail = detail;
        this.isDeleted = false;
    }

    enum Role implements GrantedAuthority {

        MEMBER(ROLES.MEMBER, "멤버권한"),
        ADMIN(ROLES.ADMIN, "관리자권한");

        public static class ROLES {
            public static final String MEMBER = "ROLE_MEMBER";
            public static final String ADMIN = "ROLE_ADMIN";
        }

        private final String authority;

        private final String description;

        Role(String authority, String description) {
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
}
