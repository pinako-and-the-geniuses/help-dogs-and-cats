package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
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

    public void modify(VolunteerDto.VolunteerAuth volunteerAuth) {
        this.content = volunteerAuth.getContent();
        modifyStatusToRequest();
    }

    public void modifyStatusToRequest() {
        this.status = Status.REQUEST;
    }

    public void modifyStatusToReject() {
        this.status = Status.REJECT;
    }

    public void modifyStatusToDone() {
        this.status = Status.DONE;
    }

    public VolunteerAuthDto.Response toResponseDto() {
        return VolunteerAuthDto.Response.builder()
                .seq(seq)
                .content(content)
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }
}
