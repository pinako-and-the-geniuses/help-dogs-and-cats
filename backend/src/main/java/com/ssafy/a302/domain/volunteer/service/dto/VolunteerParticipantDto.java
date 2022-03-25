package com.ssafy.a302.domain.volunteer.service.dto;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class VolunteerParticipantDto {
    // 봉사활동 사진 추가해야함
    private Volunteer volunteer;

    private Member member;

    private Boolean approve;

    @Builder
    public VolunteerParticipantDto(Volunteer volunteer, Member member, Boolean approve){
        this.volunteer = volunteer;
        this.member = member;
        this.approve = approve;
    }



    @Getter
    @ToString(of = {"volunteer", "member"})
    public static class Response {

        @Schema(name = "seq", title = "봉사활동 기본키", description = "봉사활동이 가지고 있는 고유 식별키입니다.")
        private final Volunteer volunteer;

        @Schema(name = "title", title = "제목", description = "제목입니다.")
        private final Member member;

        @Builder
        public Response(Volunteer volunteer, Member member) {
            this.volunteer = volunteer;
            this.member = member;
        }
    }
}
