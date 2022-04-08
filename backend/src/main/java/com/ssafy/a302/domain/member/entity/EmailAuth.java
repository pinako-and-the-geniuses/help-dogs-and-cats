package com.ssafy.a302.domain.member.entity;

import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_email_auth"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(EmailAuthSeq.class)
public class EmailAuth extends BaseCreatedEntity {

    @Id
    private String email;

    @Id
    private String authKey;

    @Builder
    public EmailAuth(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }
}