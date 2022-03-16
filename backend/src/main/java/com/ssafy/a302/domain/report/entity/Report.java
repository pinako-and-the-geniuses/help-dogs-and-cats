package com.ssafy.a302.domain.report.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_report"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "category", "content", "isProcess"})
public class Report extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @JoinColumn(name = "reporter", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member reporter;

    @JoinColumn(name = "respondent", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member respondent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isProcess;

    @OneToOne(mappedBy = "report", fetch = EAGER, cascade = ALL)
    private CautionHistory cautionHistory;

    @Builder
    public Report(Member reporter, Member respondent, Category category, String content) {
        this.reporter = reporter;
        this.respondent = respondent;
        this.category = category;
        this.content = content;
        this.isProcess = false;
    }

    public enum Category {
        NO_SHOW("잠수, 약속 불이행"),
        SEXUAL_ABUSING("성희롱"),
        ABUSING("폭언, 욕설"),
        VIOLENCE("폭력, 폭행");

        private final String description;

        Category(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }
    }




}
