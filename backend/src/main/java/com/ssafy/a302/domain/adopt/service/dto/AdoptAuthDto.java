package com.ssafy.a302.domain.adopt.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class AdoptAuthDto {

    private Long seq;

    private Status status;

    private String title;

    private String content;

    @Builder
    public  AdoptAuthDto(Long seq, String title, String content, Status status){
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.status = status;
    }
    @Getter
    @ToString(of = {"seq", "content", "content", "memberSeq"})
    public static class Response {

        @Schema(name = "seq", title = "입양인증 기본키", description = "입양인증이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "title", title = "제목", description = "제목입니다.")
        private final String title;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(title = "인증 상태", description = "인증 상태입니다.")
        private final Status status;

        @Schema(name = "memberSeq", title = "작성자 Seq", description = "작성자 Seq입니다.")
        private final Long memberSeq;

        @Builder
        public Response(Long seq, String title, String content, Status status, Long memberSeq) {
            this.seq = seq;
            this.title = title;
            this.content = content;
            this.status = status;
            this.memberSeq = memberSeq;
        }
    }

    @Getter
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "adoptAuthForPages"})
    public static class AdoptAuthPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        private List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage> adoptAuthForPages;

        @Builder
        public AdoptAuthPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage> adoptAuthForPages) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.adoptAuthForPages = adoptAuthForPages;
        }

        @Getter
        @ToString(of = {"adoptAuthSeq", "title", "status"})
        public static class AdoptAuthForPage {

            private Long adoptAuthSeq;

            private String title;

            private Status status;

            @QueryProjection
            public AdoptAuthForPage(Long adoptAuthSeq, String title, Status status) {
                this.adoptAuthSeq = adoptAuthSeq;
                this.title = title;
                this.status = status;
            }
        }
    }
}
