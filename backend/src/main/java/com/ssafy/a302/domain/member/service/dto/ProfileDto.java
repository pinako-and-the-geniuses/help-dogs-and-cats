package com.ssafy.a302.domain.member.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.a302.domain.community.entity.Community.*;

public class ProfileDto {

    @Schema(name = "CommunityPage", title = "커뮤니티 활동 이력 페이지 DTO", description = "커뮤니티 활동 이력 조회 시 페이지 정보를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber"})
    public static class CommunityPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        List<ProfileDto.Community> communities;

        @Builder
        public CommunityPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<Community> communities) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.communities = communities;
        }
    }

    @Schema(name = "Community", title = "프로필용 커뮤니티 DTO", description = "프로필에서 확인할 수 있는 커뮤니티 이력 데이터를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"seq", "title", "category", "createdDate"})
    public static class Community {

        private Long seq;

        private String title;

        private Category category;

        private LocalDate createdDate;

        @QueryProjection
        public Community(Long seq, String title, Category category, LocalDateTime createdDate) {
            this.seq = seq;
            this.title = title;
            this.category = category;
            this.createdDate = createdDate.toLocalDate();
        }
    }
}
