package com.ssafy.a302.domain.member.entity;

import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_email_auth",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "authKey"),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of ={"email", "authKey"})
public class EmailAuth extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String email;

    @Column(nullable = false)
    private String authKey;

    @Builder
    public EmailAuth(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }
}