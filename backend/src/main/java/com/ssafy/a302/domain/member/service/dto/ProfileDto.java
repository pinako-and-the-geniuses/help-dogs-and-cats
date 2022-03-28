package com.ssafy.a302.domain.member.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302.global.enums.Status;
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
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "communities"})
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

    @Schema(name = "AdoptPage", title = "입양 이력 페이지 DTO", description = "입양 이력 조회 시 페이지 정보를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "adopts"})
    public static class AdoptPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        List<ProfileDto.Adopt> adopts;

        @Builder
        public AdoptPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<Adopt> adopts) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.adopts = adopts;
        }
    }

    @Schema(name = "Adopt", title = "프로필용 입양 이력 DTO", description = "프로필에서 확인할 수 있는 입양 이력 데이터를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"seq", "title", "status"})
    public static class Adopt {

        private Long seq;

        private String title;

        private Status status;

        @QueryProjection
        public Adopt(Long seq, String title, Status status) {
            this.seq = seq;
            this.title = title;
            this.status = status;
        }
    }

    @Schema(name = "VolunteerPage", title = "봉사활동 이력 페이지 DTO", description = "봉사활동 이력 조회 시 페이지 정보를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "volunteers"})
    public static class VolunteerPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        List<ProfileDto.Volunteer> volunteers;

        @Builder
        public VolunteerPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<Volunteer> volunteers) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.volunteers = volunteers;
        }
    }

    @Schema(name = "Volunteer", title = "프로필용 봉사활동 DTO", description = "프로필에서 확인할 수 있는 봉사활동 이력 데이터를 가지고 있는 DTO 입니다.")
    @Getter
    @ToString(of = {"volunteerSeq", "memberSeq", "title", "volunteerStatus", "authStatus", "createdDate"})
    public static class Volunteer {

        private Long volunteerSeq;

        private Long memberSeq;

        private String title;

        private com.ssafy.a302.domain.volunteer.entity.Volunteer.Status volunteerStatus;

        private Status authStatus;

        private LocalDate createdDate;

        @QueryProjection
        public Volunteer(Long volunteerSeq, Long memberSeq, String title, com.ssafy.a302.domain.volunteer.entity.Volunteer.Status volunteerStatus, Status authStatus, LocalDateTime createdDate) {
            this.volunteerSeq = volunteerSeq;
            this.memberSeq = memberSeq;
            this.title = title;
            this.volunteerStatus = volunteerStatus;
            this.authStatus = authStatus;
            this.createdDate = createdDate.toLocalDate();
        }
    }
}
