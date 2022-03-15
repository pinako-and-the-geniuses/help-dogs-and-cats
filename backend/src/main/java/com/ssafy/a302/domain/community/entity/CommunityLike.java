package com.ssafy.a302.domain.community.entity;

import com.ssafy.a302.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_community_like"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(CommunityMemberSeq.class)
public class CommunityLike {

    @Id
    @JoinColumn(name = "community_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Community community;

    @Id
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    public CommunityLike(Community community, Member member) {
        this.community = community;
        this.member = member;
    }
}
