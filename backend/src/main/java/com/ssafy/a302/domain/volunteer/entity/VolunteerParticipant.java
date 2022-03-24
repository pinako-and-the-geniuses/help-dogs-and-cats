package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerParticipantDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "tb_volunteer_participant"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(VolunteerMemberSeq.class)
public class VolunteerParticipant {

    @Id
    @JoinColumn(name = "volunteer_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Volunteer volunteer;

    @Id
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    private Member member;

    private Boolean approve;

    @Builder
    public VolunteerParticipant(Volunteer volunteer, Member member) {
        this.volunteer = volunteer;
        this.member = member;
        this.approve = false;
    }

    public VolunteerParticipantDto.Response toResponseDto() {
        return VolunteerParticipantDto.Response.builder()
                .volunteer(volunteer)
                .member(member)
                .build();
    }

    public void changeParticipantIsApprove(Boolean approve){
        this.approve = approve;
    }


}
