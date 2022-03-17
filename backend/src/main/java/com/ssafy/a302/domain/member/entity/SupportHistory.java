package com.ssafy.a302.domain.member.entity;

import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_support_history"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "email", "amount", "name"})
public class SupportHistory extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_history_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer amount;

    private String name;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    public SupportHistory(String email, Integer amount, String name, Member member) {
        this.email = email;
        this.amount = amount;
        this.name = name;
        this.member = member;
    }
}
