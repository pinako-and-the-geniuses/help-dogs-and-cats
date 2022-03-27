package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
        name = "tb_volunteer"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "title", "content", "viewCount", "category", "activityArea", "status", "minParticipantCount", "maxParticipantCount", "isDeleted"})
public class Volunteer extends BaseLastModifiedEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "volunteer_seq", columnDefinition = "BIGINT UNSIGNED")
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

    @Column(nullable = false)
    private String activityArea;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer minParticipantCount;

    @Column(nullable = false ,columnDefinition = "INT UNSIGNED")
    private Integer maxParticipantCount;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "volunteer", cascade = ALL)
    private List<VolunteerComment> volunteerComments = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer", cascade = ALL)
    private List<VolunteerParticipant> volunteerParticipants = new ArrayList<>();

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "volunteer", cascade = ALL)
    private VolunteerAuth volunteerAuth;

    @Builder
    public Volunteer(String title, String content, String activityArea, Category category, Integer minParticipantCount, Integer maxParticipantCount, Member member) {
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.category = category;
        this.activityArea = activityArea;
        this.status = Status.RECRUITING;
        this.minParticipantCount = minParticipantCount;
        this.maxParticipantCount = maxParticipantCount;
        this.isDeleted = false;
        this.member = member;
    }

    // 봉사활동 수정
    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateActivityArea(String activityArea){
        this.activityArea = activityArea;
    }

    public void updateCategory(Category category){
        this.category = category;
    }

    public void updateMinParticipantCount(Integer minParticipantCount){
        this.minParticipantCount = minParticipantCount;
    }

    public void updateMaxParticipantCount(Integer maxParticipantCount){
        this.maxParticipantCount = maxParticipantCount;
    }




    // 봉사활동 신청
    public void apply(Member member){
        this.member = member;
    }

    public void changeVolunteerStatus(Status status){
        this.status = status;
    }

    public void setCreator(Member member){
        this.member = member;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void createAuth(VolunteerAuth volunteerAuth) {
        this.volunteerAuth = volunteerAuth;
    }

    public enum Category {

        SHELTER("보호소");

        private final String description;

        Category (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Status {

        RECRUITING("모집중"),
        ONGOING("모집 완료"),
        DONE("완료");

        private final String description;

        Status (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public VolunteerDto.Response toResponseDto() {
        return VolunteerDto.Response.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .category(category)
                .activityArea(activityArea)
                .minParticipantCount(minParticipantCount)
                .maxParticipantCount(maxParticipantCount)
                .build();
    }


}
