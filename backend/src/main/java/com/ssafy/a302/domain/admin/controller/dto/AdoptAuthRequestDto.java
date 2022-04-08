package com.ssafy.a302.domain.admin.controller.dto;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class AdoptAuthRequestDto {
    @Schema(name = "입양 인증 조치 요청 DTO", description = "입양 인증 조치 API 호출 시 사용되는 요청 DTO 입니다.")
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

        public AdoptAuthDto toServiceDto(){
            return AdoptAuthDto.builder()
                    .status(Status.valueOf(status.toUpperCase()))
                    .build();
        }
    }
}
