package com.ssafy.a302.domain.community.service.dto;

import com.ssafy.a302.global.constant.Path;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CommunityCommentDto {

    @Getter
    @ToString(of = {"content", "parentSeq"})
    public static class RegisterInfo {

        private final String content;

        private final Long parentSeq;

        @Builder
        public RegisterInfo(String content, Long parentSeq) {
            this.content = content;
            this.parentSeq = parentSeq;
        }
    }

    @Schema(name = "detail", title = "커뮤니티 게시글 상세페이지 조회용 댓글 DTO", description = "커뮤니티 게시글 상세페이지 조회시 사용하는 댓글 DTO 입니다.")
    @Getter
    @ToString(of = {"commentSeq", "content", "createdDate", "writerSeq", "writerNickname", "writerProfileImagePath", "parentSeq", "children"})
    public static class ForDetail {

        @Schema(name = "commentSeq", title = "댓글 식별키", description = "댓글 식별키입니다.")
        private Long commentSeq;

        @Schema(name = "content", title = "댓글 본문", description = "본문입니다.")
        private String content;

        @Schema(name = "createdDate", title = "댓글 작성일", description = "댓글 작성일입니다.")
        private LocalDate createdDate;

        @Schema(name = "writerSeq", title = "작성자 식별키", description = "작성자 식별키입니다.")
        private Long writerSeq;

        @Schema(name = "writerNickname", title = "작성자 닉네임", description = "작성자 닉네임입니다.")
        private String writerNickname;

        @Schema(name = "writerProfileImagePath", title = "작성자 프로필 이미지 경로", description = "작성자 프로필 이미지 경로 입니다.")
        private String writerProfileImagePath;

        @Schema(name = "parentSeq", title = "부모 댓글 식별키", description = "부모 댓글 식별키입니다.")
        private Long parentSeq;

        @Schema(name = "children", title = "대댓글 목록", description = "대댓글 목록입니다.")
        private List<ForDetail> children;

        @Builder
        public ForDetail(Long commentSeq, String content, LocalDate createdDate, Long writerSeq, String writerNickname, String writerProfileImageFilename, Long parentSeq, List<ForDetail> children) {
            this.commentSeq = commentSeq;
            this.content = content;
            this.createdDate = createdDate;
            this.writerSeq = writerSeq;
            this.writerNickname = writerNickname;
            this.writerProfileImagePath = Path.PROFILE_IMAGE_ACCESS_PATH + "/" + writerProfileImageFilename;
            this.parentSeq = parentSeq;
            this.children = children;
        }
    }
}
