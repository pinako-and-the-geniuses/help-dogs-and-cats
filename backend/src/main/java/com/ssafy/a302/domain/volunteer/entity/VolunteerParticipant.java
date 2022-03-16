package com.ssafy.a302.domain.volunteer.entity;

import com.ssafy.a302.domain.member.entity.Member;
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

    @Builder
    public VolunteerParticipant(Volunteer volunteer, Member member) {
        this.volunteer = volunteer;
        this.member = member;
    }
}
