package com.ssafy.a302.domain.community.service.dto;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Schema(name = "커뮤니티 DTO", description = "커뮤니티 DTO 입니다.")
@SuperBuilder
@Getter
@ToString(of = {"seq", "title", "content", "category"})
public class CommunityDto {

    @Schema(name = "seq", title = "커뮤니티 식별키", description = "커뮤니티 게시글의 식별키입니다.")
    private Long seq;

    @Schema(name = "title", title = "제목", description = "커뮤니티 게시글 제목입니다.")
    private String title;

    @Schema(name = "content", title = "본문", description = "커뮤니티 게시글 본문입니다.")
    private String content;

    @Schema(name = "viewCount", title = "조회수", description = "커뮤니티 조회수입니다.")
    private Long viewCount;

    @Schema(name = "category", title = "카테고리", description = "커뮤니티 게시글의 카테고리입니니다.")
    private Community.Category category;

    @Schema(name = "isDeleted", title = "삭제 여부", description = "커뮤니티 삭제 여부입니다.")
    private boolean isDeleted;

    @Builder
    public CommunityDto(Long seq, String title, String content, Community.Category category) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Community toEntity(Member writer) {
        return Community.builder()
                .title(title)
                .content(content)
                .category(category)
                .member(writer)
                .build();
    }
}
