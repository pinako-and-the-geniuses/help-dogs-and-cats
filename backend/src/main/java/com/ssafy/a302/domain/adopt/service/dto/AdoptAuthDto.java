package com.ssafy.a302.domain.adopt.service.dto;

import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
public class AdoptAuthDto {

    private Long seq;

    private String title;

    private String content;

    @Builder
    public  AdoptAuthDto(Long seq, String title, String content){
        this.seq = seq;
        this.title = title;
        this.content = content;
    }
    @Getter
    @ToString(of = {"seq", "content", "lastModifiedDate"})
    public static class Response {

        @Schema(name = "seq", title = "입양인증 기본키", description = "입양인증이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "title", title = "제목", description = "제목입니다.")
        private final String title;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(name = "memberSeq", title = "작성자 Seq", description = "작성자 Seq입니다.")
        private final Long memberSeq;


        @Builder
        public Response(Long seq, String title, String content, Long memberSeq) {
            this.seq = seq;
            this.title = title;
            this.content = content;
            this.memberSeq = memberSeq;
        }
    }
}
