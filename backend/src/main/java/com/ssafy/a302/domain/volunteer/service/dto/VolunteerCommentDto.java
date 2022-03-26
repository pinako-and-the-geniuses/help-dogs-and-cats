package com.ssafy.a302.domain.volunteer.service.dto;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class VolunteerCommentDto {
    private String content;

    private Long parentSeq;

    private boolean isDeleted;

    private Volunteer volunteer;

    private Member member;

    private VolunteerComment parent;

    private List<VolunteerComment> children;

    @Builder
    public VolunteerCommentDto(String content, Long parentSeq, boolean isDeleted, Volunteer volunteer, Member member, VolunteerComment parent, List<VolunteerComment> children) {
        this.content = content;
        this.parentSeq = parentSeq;
        this.isDeleted = isDeleted;
        this.volunteer = volunteer;
        this.member = member;
        this.parent = parent;
        this.children = children;
    }

    public VolunteerComment toEntity(){
        return VolunteerComment.builder()
                .content(content)
                .build();
    }


    @Getter
    @ToString(of = {"seq", "content", "isDeleted", "volunteer", "member", "parent", "children"})
    public static class Response {

        private Long seq;

        private String content;

        private boolean isDeleted;

        private Volunteer volunteer;

        private Member member;

        private VolunteerComment parent;

        private List<VolunteerComment> children;

        @Builder
        public Response(Long seq, String content, boolean isDeleted, Volunteer volunteer, Member member, VolunteerComment parent, List<VolunteerComment> children) {
            this.seq = seq;
            this.content = content;
            this.isDeleted = isDeleted;
            this.volunteer = volunteer;
            this.member = member;
            this.parent = parent;
            this.children = children;
        }
    }
}
