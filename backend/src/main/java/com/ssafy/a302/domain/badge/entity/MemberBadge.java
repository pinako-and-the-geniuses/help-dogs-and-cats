package com.ssafy.a302.domain.badge.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "tb_member_badge"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(MemberBadgeSeq.class)
public class MemberBadge extends BaseCreatedEntity {

    @Id
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Id
    @JoinColumn(name = "badge_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Badge badge;

    @Builder
    public MemberBadge(Member member, Badge badge) {
        this.member = member;
        this.badge = badge;
    }
}
