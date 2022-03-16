package com.ssafy.a302.domain.member.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_member_detail",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nickname"),
                @UniqueConstraint(columnNames = "tel"),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "nickname", "exp", "tel", "activityArea", "imageInfo"})
public class MemberDetail {

    @Id
    private Long seq;

    @MapsId
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @OneToOne(fetch = EAGER)
    private Member member;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer exp;

    @Column(nullable = false, length = 13)
    private String tel;

    @Column(nullable = false)
    private String activityArea;

    @Column(columnDefinition = "TEXT")
    private String imageInfo;

    @Builder
    public MemberDetail(Member member, String nickname, String tel, String activityArea, String imageInfo) {
        this.member = member;
        this.nickname = nickname;
        this.exp = 0;
        this.tel = tel;
        this.activityArea = activityArea;
        this.imageInfo = imageInfo;

        member.createDetail(this);
    }
}
