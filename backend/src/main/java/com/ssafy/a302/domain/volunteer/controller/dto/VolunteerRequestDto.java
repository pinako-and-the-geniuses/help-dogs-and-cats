package com.ssafy.a302.domain.volunteer.controller.dto;

import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(name = "봉사활동 요청 DTO", description = "봉사활동 API 호출 시 사용되는 요청 DTO 입니다.")
@Getter
public class VolunteerRequestDto {

    @Schema(name = "봉사활동 요청 DTO", description = "봉사활동 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"title", "content", "activityArea", "authTime", "contact", "endDate", "minParticipantCount", "maxParticipantCount"})
    public static class RegisterInfo{
        // 봉사활동 사진 추가해야함
        private String title;

        private String content;

        private String activityArea;

        private String authTime;

        private String contact;

        private String endDate;

//        private String category;

        private Integer minParticipantCount;

        private Integer maxParticipantCount;

        @Builder
        public RegisterInfo(String title, String content, String activityArea, String authTime, String contact, String endDate, Integer minParticipantCount, Integer maxParticipantCount){
            this.title = title;
            this.content = content;
//            this.category = category;
            this.activityArea = activityArea;
            this.authTime = authTime;
            this.contact = contact;
            this.endDate = endDate;
            this.minParticipantCount = minParticipantCount;
            this.maxParticipantCount = maxParticipantCount;
        }

        public VolunteerDto toServiceDto(){
            return VolunteerDto.builder()
                    .title(title)
                    .content(content)
//                    .category(Volunteer.Category.valueOf(category))
                    .activityArea(activityArea)
                    .authTime(authTime)
                    .contact(contact)
                    .endDate(endDate)
                    .minParticipantCount(minParticipantCount)
                    .maxParticipantCount(maxParticipantCount)
                    .build();
        }
    }

    @Schema(name = "봉사활동 상세페이지 수정 요청 DTO", description = "봉사활동 상세페이지 수정 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"title", "content", "activityArea", "minParticipantCount", "maxParticipantCount"})
    public static class UpdateInfo{
        // 봉사활동 사진 추가해야함
        private String title;

        private String content;

        private String activityArea;

        private String authTime;

        private String contact;

        private String endDate;

//        private String category;
        private Integer minParticipantCount;

        private Integer maxParticipantCount;

        @Builder
        public UpdateInfo(String title, String content, String activityArea, String authTime, String contact, String endDate, Integer minParticipantCount, Integer maxParticipantCount){
            this.title = title;
            this.content = content;
//            this.category = category;
            this.activityArea = activityArea;
            this.authTime = authTime;
            this.contact = contact;
            this.endDate = endDate;
            this.minParticipantCount = minParticipantCount;
            this.maxParticipantCount = maxParticipantCount;
        }

        public VolunteerDto toServiceDto(){
            return VolunteerDto.builder()
                    .title(title)
                    .content(content)
//                    .category(Volunteer.Category.valueOf(category))
                    .authTime(authTime)
                    .contact(contact)
                    .endDate(endDate)
                    .activityArea(activityArea)
                    .minParticipantCount(minParticipantCount)
                    .maxParticipantCount(maxParticipantCount)
                    .build();
        }
    }

    @Schema(name = "봉사활동 진행상태 변경 요청 DTO", description = "봉사활동 진행상태 변경 API 호출 시 사용되는 요청 DTO 입니다.")
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

        public VolunteerDto toServiceDto(){
            return VolunteerDto.builder()
                    .status(Volunteer.Status.valueOf(status))
                    .build();
        }
    }
}
