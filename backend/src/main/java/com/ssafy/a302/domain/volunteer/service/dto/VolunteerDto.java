package com.ssafy.a302.domain.volunteer.service.dto;

import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.service.VolunteerServiceImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class VolunteerDto {

    // 봉사활동 사진 추가해야함
    private String title;

    private String content;

    private String activityArea;

    private Volunteer.Category category;

    private Volunteer.Status status;

    private Integer minParticipantCount;

    private Integer maxParticipantCount;

    @Builder
    public VolunteerDto(String title, String content, Volunteer.Category category, String activityArea, Volunteer.Status status, Integer minParticipantCount, Integer maxParticipantCount){
        this.title = title;
        this.content = content;
        this.category = category;
        this.activityArea = activityArea;
        this.status = status;
        this.minParticipantCount = minParticipantCount;
        this.maxParticipantCount = maxParticipantCount;
    }

    public Volunteer toEntity(){
        return Volunteer.builder()
                .title(title)
                .content(content)
                .category(category)
                .activityArea(activityArea)
                .minParticipantCount(minParticipantCount)
                .maxParticipantCount(maxParticipantCount)
                .build();
    }


    @Getter
    @ToString(of = {"seq", "title", "content", "category", "activityArea", "minParticipantCount", "maxParticipantCount"})
    public static class Response {

        @Schema(name = "seq", title = "봉사활동 기본키", description = "봉사활동이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "title", title = "제목", description = "제목입니다.")
        private final String title;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(name = "category", title = "카테고리", description = "봉사활동이 가지고 있는 카테고리입니다.")
        private final Volunteer.Category category;

        @Schema(name = "activityArea", title = "활동지역", description = "활동지역입니다.")
        private final String activityArea;

        @Schema(name = "minParticipantCount", title = "최소인원", description = "최소인원입니다.")
        private final Integer minParticipantCount;

        @Schema(name = "maxParticipantCount", title = "최대인원", description = "최대인원입니다.")
        private final Integer maxParticipantCount;

        @Builder
        public Response(Long seq, String title, String content, Volunteer.Category category, String activityArea, Integer minParticipantCount, Integer maxParticipantCount) {
            this.seq = seq;
            this.title = title;
            this.content = content;
            this.category = category;
            this.activityArea = activityArea;
            this.minParticipantCount = minParticipantCount;
            this.maxParticipantCount = maxParticipantCount;
        }
    }

    @Getter
    @ToString(of = {"memberSeq", "nickname", "title", "content", "status", "activityArea", "volunteerComment"})
    public static class DetailResponse {

        private final Long memberSeq;

        private final String nickname;

        private final String title;

        private final String content;

        private final Volunteer.Status status;

        private final String activityArea;

        private List<VolunteerServiceImpl.SimpleVolunteerCommentDto> volunteerComment;

        public void setVolunteerComment(List<VolunteerServiceImpl.SimpleVolunteerCommentDto> volunteerComment){
            this.volunteerComment = volunteerComment;
        }

        @Builder
        public DetailResponse(Long memberSeq, String nickname, String title, String content, Volunteer.Status status, String activityArea, List<VolunteerComment> volunteerComment) {
            this.memberSeq = memberSeq;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.status = status;
            this.activityArea = activityArea;
        }
    }

}
