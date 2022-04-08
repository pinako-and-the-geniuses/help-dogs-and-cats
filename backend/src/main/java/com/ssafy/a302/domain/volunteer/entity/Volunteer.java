package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
        name = "tb_volunteer"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "title", "content", "viewCount", "activityArea", "authTime", "contact", "endDate", "status", "minParticipantCount", "maxParticipantCount", "isDeleted"})
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

    @Column(nullable = false)
    private String activityArea;

    @Column(nullable = false)
    private String authTime;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate endDate;

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

    @OneToMany(mappedBy = "volunteer", cascade = ALL, orphanRemoval = true)
    private List<VolunteerComment> volunteerComments = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer", cascade = ALL, orphanRemoval = true)
    private List<VolunteerParticipant> volunteerParticipants = new ArrayList<>();

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "volunteer", cascade = ALL, orphanRemoval = true)
    private VolunteerAuth volunteerAuth;

    @Builder
    public Volunteer(String title, String content, String activityArea, String authTime, String contact, LocalDate endDate, Integer minParticipantCount, Integer maxParticipantCount, Member member) {
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.activityArea = activityArea;
        this.authTime = authTime;
        this.contact = contact;
        this.endDate = endDate;
        this.status = Status.RECRUITING;
        this.minParticipantCount = minParticipantCount;
        this.maxParticipantCount = maxParticipantCount;
        this.isDeleted = false;
        this.member = member;
    }

    // 봉사활동 신청
    public void apply(Member member){
        this.member = member;
    }

    public void changeVolunteerStatus(Status status){
        this.status = status;
    }


    public void delete() {
        this.isDeleted = true;
    }

    public void createAuth(VolunteerAuth volunteerAuth) {
        this.volunteerAuth = volunteerAuth;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void modify(VolunteerDto volunteerDto) {
        this.title = volunteerDto.getTitle();
        this.content = volunteerDto.getContent();
        this.endDate = volunteerDto.getEndDate();
        this.activityArea = volunteerDto.getActivityArea();
        this.authTime = volunteerDto.getAuthTime();
        this.contact = volunteerDto.getContact();
        this.minParticipantCount = volunteerDto.getMinParticipantCount();
        this.maxParticipantCount = volunteerDto.getMaxParticipantCount();
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
                .activityArea(activityArea)
                .authTime(authTime)
                .contact(contact)
                .endDate(endDate)
                .minParticipantCount(minParticipantCount)
                .maxParticipantCount(maxParticipantCount)
                .build();
    }
}
