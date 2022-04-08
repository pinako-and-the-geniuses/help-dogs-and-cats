package com.ssafy.a302.domain.community.controller.dto;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.global.validator.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Schema(name = "커뮤니티 요청 DTO", description = "커뮤니티 API 호출 시 사용되는 요청 DTO 입니다.")
@Getter
public class CommunityRequestDto {

    @Schema(name = "커뮤니티 게시글 등록 요청 DTO", description = "커뮤니티 게시글 등록 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"title", "content", "category"})
    public static class RegisterInfo {

        @Schema(name = "title", title = "제목", description = "게시글 제목입니다.", example = "입양 후기입니다.", required = true)
        @NotBlank(message = "${pattern.blank}")
        private String title;

        @Schema(name = "content", title = "본문", description = "본문입니다.", example = "<p>지난 1월 1일에 입양을 했습니다.</p>", required = true)
        @NotBlank(message = "${pattern.blank}")
        private String content;

        @Schema(name = "category", title = "카테고리", description = "게시글 카테고리입니다.", example = "제보", required = true)
        @EnumValidator(enumClass = Community.Category.class, ignoreCase = true, message = "${pattern.category}")
        private String category;

        @Builder
        public RegisterInfo(String title, String content, String category) {
            this.title = title;
            this.content = content;
            this.category = category;
        }

        public CommunityDto toServiceDto() {
            return CommunityDto.builder()
                    .title(title)
                    .content(content)
                    .category(Community.Category.valueOf(category.toUpperCase()))
                    .build();
        }
    }

    @Schema(name = "커뮤니티 게시글 수정 요청 DTO", description = "커뮤니티 게시글 수정 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"title", "content", "category"})
    public static class ModifyInfo {

        @Schema(name = "title", title = "제목", description = "게시글 제목입니다.", example = "입양 후기입니다.", required = true)
        @NotBlank(message = "${pattern.blank}")
        private String title;

        @Schema(name = "content", title = "본문", description = "본문입니다.", example = "<p>지난 1월 1일에 입양을 했습니다.</p>", required = true)
        @NotBlank(message = "${pattern.blank}")
        private String content;

        @Schema(name = "category", title = "카테고리", description = "게시글 카테고리입니다.", example = "제보", required = true)
        @EnumValidator(enumClass = Community.Category.class, ignoreCase = true, message = "${pattern.category}")
        private String category;

        @Builder
        public ModifyInfo(String title, String content, String category) {
            this.title = title;
            this.content = content;
            this.category = category;
        }

        public CommunityDto toServiceDto() {
            return CommunityDto.builder()
                    .title(title)
                    .content(content)
                    .category(Community.Category.valueOf(category.toUpperCase()))
                    .build();
        }
    }
}
