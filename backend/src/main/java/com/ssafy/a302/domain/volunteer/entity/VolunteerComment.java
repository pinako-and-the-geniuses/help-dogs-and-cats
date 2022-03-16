package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
        name = "tb_volunteer_comment"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "content", "isDeleted"})
public class VolunteerComment extends BaseLastModifiedEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @JoinColumn(name = "volunteer_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Volunteer volunteer;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "parent_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private VolunteerComment parent;

    @OneToMany(mappedBy = "parent", cascade = ALL)
    private List<VolunteerComment> children = new ArrayList<>();

    @Builder
    public VolunteerComment(String content, Volunteer volunteer, Member member) {
        this.content = content;
        this.isDeleted = false;
        this.volunteer = volunteer;
        this.member = member;
    }

    public void createParent(VolunteerComment parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
