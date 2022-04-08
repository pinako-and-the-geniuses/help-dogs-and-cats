package com.ssafy.a302.domain.volunteer.controller.dto;

import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "VolunteerAuthRequestDto", title = "봉사활동 인증 Request DTO", description = "봉사활동 인증 관련 REST API가 호출될 떄 사용되는 DTO 입니다.")
public class VolunteerAuthRequestDto {

    @Schema(name = "RequestInfo", title = "봉사활동 인증 요청 DTO", description = "봉사활동 인증 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"content", "authenticatedParticipantSequences"})
    public static class RequestInfo {

        @Schema(title = "인증 내용", description = "봉사활동 인증 내용입니다.")
        @NotBlank(message = "{pattern.blank}")
        private String content;

        @Schema(title = "봉사활동 인증 인원 식별키", description = "봉사활동 인증 인원 식별키 리스트입니다.")
        @NotNull(message = "{pattern}")
        private List<Long> authenticatedParticipantSequences;

        @Builder
        public RequestInfo(String content, List<Long> authenticatedParticipantSequences) {
            this.content = content;
            this.authenticatedParticipantSequences = authenticatedParticipantSequences;
        }

        public VolunteerDto.VolunteerAuth toServiceDto() {
            return VolunteerDto.VolunteerAuth.builder()
                    .content(content)
                    .authenticatedParticipantSequences(authenticatedParticipantSequences)
                    .build();
        }
    }

    @Schema(name = "ModifyInfo", title = "봉사활동 인증 수정 요청 DTO", description = "봉사활동 인증 수정 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"content", "authenticatedParticipantSequences"})
    public static class ModifyInfo {

        @Schema(name = "content", title = "인증 내용", description = "봉사활동 인증 내용입니다.")
        @NotBlank(message = "{pattern.blank}")
        private String content;

        @Schema(title = "봉사활동 인증 인원 식별키", description = "봉사활동 인증 인원 식별키 리스트입니다.")
        @NotNull(message = "{pattern}")
        private List<Long> authenticatedParticipantSequences;

        @Builder
        public ModifyInfo(String content, List<Long> authenticatedParticipantSequences) {
            this.content = content;
            this.authenticatedParticipantSequences = authenticatedParticipantSequences;
        }

        public VolunteerDto.VolunteerAuth toServiceDto() {
            return VolunteerDto.VolunteerAuth.builder()
                    .content(content)
                    .authenticatedParticipantSequences(authenticatedParticipantSequences)
                    .build();
        }
    }
}
