package com.ssafy.a302.domain.admin.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302.global.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    @ToString(of = {"seq", "content", "memberSeq", "nickname", "createdDate"})
    public static class Response {

        @Schema(name = "seq", title = "봉사활동인증 기본키", description = "봉사활동인증이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(title = "제목", description = "제목입니다.")
        private final String title;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(title = "인증 상태", description = "인증 상태입니다.")
        private final Status status;

        @Schema(title = "작성자 식별키", description = "작성자 식별키입니다.")
        private final Long memberSeq;

        @Schema(title = "작성자 닉네임", description = "작성자 닉네임입니다.")
        private final String nickname;

        @Schema(title = "작성일", description = "작성일입니다.")
        private final LocalDate createdDate;

        @Builder
        public Response(Long seq, String title, String content, Status status, Long memberSeq, String nickname, LocalDate createdDate) {
            this.seq = seq;
            this.title = title;
            this.content = content;
            this.status = status;
            this.memberSeq = memberSeq;
            this.nickname = nickname;
            this.createdDate = createdDate;
        }
    }

    @Getter
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "volunteerAuthForPages"})
    public static class VolunteerAuthPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        private List<VolunteerAuthForPage> volunteerAuthForPages;

        @Builder
        public VolunteerAuthPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<VolunteerAuthForPage> volunteerAuthForPages) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.volunteerAuthForPages = volunteerAuthForPages;
        }

        @Getter
        @ToString(of = {"volunteerAuthSeq", "title", "status"})
        public static class VolunteerAuthForPage {

            private Long volunteerAuthSeq;

            private String title;

            private Status status;

            @QueryProjection
            public VolunteerAuthForPage(Long volunteerAuthSeq, String title, Status status) {
                this.volunteerAuthSeq = volunteerAuthSeq;
                this.title = title;
                this.status = status;
            }
        }
    }
}
