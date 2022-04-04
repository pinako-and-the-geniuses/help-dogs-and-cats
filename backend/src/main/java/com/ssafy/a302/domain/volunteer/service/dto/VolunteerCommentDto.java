package com.ssafy.a302.domain.volunteer.service.dto;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.global.constant.Path;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
public class VolunteerCommentDto {

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

    @Schema(name = "detail", title = "봉사활동 상세페이지 조회용 댓글 DTO", description = "봉사활동 상세페이지 조회시 사용하는 댓글 DTO 입니다.")
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
            this.writerProfileImagePath = writerProfileImageFilename == null ? null : Path.PROFILE_IMAGE_ACCESS_PATH + "/" + writerProfileImageFilename;
            this.parentSeq = parentSeq;
            this.children = children;
        }
    }

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
