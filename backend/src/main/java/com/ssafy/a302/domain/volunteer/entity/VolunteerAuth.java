package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import com.ssafy.a302.global.enums.Status;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(
        name = "tb_volunteer_auth"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "content", "status"})
public class VolunteerAuth extends BaseLastModifiedEntity {

    @Id
    @JoinColumn(name = "volunteer_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @MapsId
    @JoinColumn(name = "volunteer_seq", columnDefinition = "BIGINT UNSIGNED")
    @OneToOne(fetch = EAGER)
    private Volunteer volunteer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Builder
    public VolunteerAuth(Volunteer volunteer, String content) {
        this.volunteer = volunteer;
        this.content = content;
        this.status = Status.REQUEST;

        volunteer.createAuth(this);
    }
}
