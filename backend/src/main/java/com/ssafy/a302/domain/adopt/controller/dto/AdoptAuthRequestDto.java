package com.ssafy.a302.domain.adopt.controller.dto;

import com.ssafy.a302.domain.adopt.service.dto.AdoptDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class AdoptAuthRequestDto {

    @Getter
    @NoArgsConstructor
    public static class RequestAdoptAuthInfo {

        @Schema(name = "title", title = "입양 인증 제목", description = "입양 인증 제목입니다.")
        @NotBlank(message = "${pattern.blank}")
        private String title;

        @Schema(name = "content", title = "입양 인증 본문", description = "입양 인증 본문입니다.")
        @NotBlank(message = "${pattern.blank}")
        private String content;

        @Builder
        public RequestAdoptAuthInfo(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public AdoptDto.AdoptAuth toServiceDto() {
            return AdoptDto.AdoptAuth.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

}
