package com.ssafy.a302.domain.admin.service.dto;

import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
public class VolunteerAuthDto {

    private Long seq;

    private Status status;

    private String content;

    private LocalDateTime lastModifiedDate;

    @Builder
    public  VolunteerAuthDto(Long seq, String content, LocalDateTime lastModifiedDate, Status status){
        this.seq = seq;
        this.content = content;
        this.lastModifiedDate = lastModifiedDate;
        this.status = status;
    }

    @Getter
    @ToString(of = {"seq", "content", "lastModifiedDate"})
    public static class Response {

        @Schema(name = "seq", title = "봉사활동인증 기본키", description = "봉사활동인증이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(name = "lastModifiedDate", title = "최종수정일", description = "최종수정일입니다.")
        private final LocalDateTime lastModifiedDate;

        @Builder
        public Response(Long seq, String content, LocalDateTime lastModifiedDate) {
            this.seq = seq;
            this.content = content;
            this.lastModifiedDate = lastModifiedDate;
        }
    }

}
