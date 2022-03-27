package com.ssafy.a302.domain.community.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_community"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "title", "content", "viewCount", "category", "isDeleted"})
public class Community extends BaseLastModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long viewCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "community", cascade = ALL)
    private List<CommunityLike> communityLikes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = ALL)
    private List<CommunityComment> communityComments = new ArrayList<>();

    @Builder
    public Community(String title, String content, Category category, Member member) {
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.category = category;
        this.isDeleted = false;
        this.member = member;
    }

    public enum Category {

        NOTICE("공지사항"),
        REPORT("제보"),
        GENERAL("잡담"),
        REVIEW("봉사활동/입양 후기");

        private final String description;

        Category(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public void delete() {
        this.isDeleted = true;
    }
}
