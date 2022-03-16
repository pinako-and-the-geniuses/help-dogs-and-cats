package com.ssafy.a302.domain.report.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_caution_history"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "message"})
public class CautionHistory extends BaseCreatedEntity {

    @Id
    private Long seq;

    @MapsId
    @JoinColumn(name = "caution_history_seq", columnDefinition = "BIGINT UNSIGNED")
    @OneToOne(fetch = EAGER)
    private Report report;

    @JoinColumn(name = "respondent", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member respondent;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Builder
    public CautionHistory(Member respondent, String message) {
        this.respondent = respondent;
        this.message = message;
    }
}
