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
    @Column(name = "member_seq")
    private Long seq;

    @MapsId
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @OneToOne(fetch = EAGER)
    private Member member;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer exp;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String activityArea;

    @Column(columnDefinition = "TEXT")
    private String imageInfo;

    @JoinColumn(name = "level_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Level level;

    @Builder
    public MemberDetail(String nickname, String tel, String activityArea, String imageInfo, Level level) {
        this.nickname = nickname;
        this.exp = 0;
        this.tel = tel;
        this.activityArea = activityArea;
        this.imageInfo = imageInfo;
        this.level = level;
    }
}
