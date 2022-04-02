package com.ssafy.a302.domain.admin.controller.dto;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class VolunteerAuthRequestDto {
    @Schema(name = "봉사활동 인증 조치 요청 DTO", description = "봉사활동 인증 조치 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"status"})
    public static class StatusInfo{
        // 봉사활동 사진 추가해야함
        private String status;

        @Builder
        public StatusInfo(String status){
            this.status = status;
        }

        public VolunteerAuthDto toServiceDto(){
            return VolunteerAuthDto.builder()
                    .status(Status.valueOf(status.toUpperCase()))
                    .build();
        }
    }
}
