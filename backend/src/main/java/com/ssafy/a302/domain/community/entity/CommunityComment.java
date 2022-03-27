package com.ssafy.a302.domain.community.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "tb_community_comment"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "content", "isDeleted"})
public class CommunityComment extends BaseLastModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @JoinColumn(name = "community_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Community community;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "parent_seq")
    @ManyToOne(fetch = LAZY)
    private CommunityComment parent;

    @OneToMany(mappedBy = "parent", cascade = ALL)
    private List<CommunityComment> children = new ArrayList<>();

    @Builder
    public CommunityComment(String content, Community community, Member member) {
        this.content = content;
        this.isDeleted = false;
        this.community = community;
        this.member = member;
    }

    public void createParent(CommunityComment parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
