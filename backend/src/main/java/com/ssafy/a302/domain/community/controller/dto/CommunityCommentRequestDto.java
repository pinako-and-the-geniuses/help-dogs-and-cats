package com.ssafy.a302.domain.community.controller.dto;

import com.ssafy.a302.domain.community.service.dto.CommunityCommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Schema(name = "커뮤니티 게시글 댓글 요청 DTO", description = "커뮤니티 게시글 댓글 관련 API 가 호출될 떄 사용되는 DTO입니다.")
@Getter
public class CommunityCommentRequestDto {

    @Schema(name = "커뮤니티 게시글 댓글 요청 DTO", description = "커뮤니티 게시글 댓글 등록 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"content", "parentSeq"})
    public static class RegisterInfo {

        @Schema(name = "content", title = "본문", description = "본문입니다.", example = "댓글이요", required = true)
        @NotBlank(message = "${pattern.blank}")
        private String content;

        @Schema(name = "parentSeq", title = "부모 댓글 식별키", description = "부모 댓글 식별키입니다.", example = "1")
        private Long parentSeq;

        @Builder
        public RegisterInfo(String content, Long parentSeq) {
            this.content = content;
            this.parentSeq = parentSeq;
        }

        public CommunityCommentDto.RegisterInfo toServiceDto() {
            return CommunityCommentDto.RegisterInfo.builder()
                    .content(content)
                    .parentSeq(parentSeq)
                    .build();
        }
    }
}
