package com.ssafy.a302.domain.volunteer.controller.dto;

import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerParticipantDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(name = "봉사활동 참여자 요청 DTO", description = "봉사활동 참여자 API 호출 시 사용되는 요청 DTO 입니다.")
@Getter
public class VolunteerParticipantRequestDto {

    @Schema(name = "봉사활동 참여자 참석여부 변경 요청 DTO", description = "봉사활동 참여자 참석여부 변경 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"approve"})
    public static class IsApproveInfo{
        // 봉사활동 사진 추가해야함
        private Boolean approve;

        @Builder
        public IsApproveInfo(Boolean approve){
            this.approve = approve;
        }

        public VolunteerParticipantDto toServiceDto(){
            return VolunteerParticipantDto.builder()
                    .approve(approve)
                    .build();
        }
    }
}
