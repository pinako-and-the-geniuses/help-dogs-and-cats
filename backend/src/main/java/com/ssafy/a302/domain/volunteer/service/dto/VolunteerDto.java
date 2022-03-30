package com.ssafy.a302.domain.volunteer.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.global.constant.Path;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class VolunteerDto {

    // 봉사활동 사진 추가해야함
    private String title;

    private String content;

    private String activityArea;

    private String authTime;

    private String contact;

    private String endDate;

    private Volunteer.Status status;

    private Integer minParticipantCount;

    private Integer maxParticipantCount;

    @Builder
    public VolunteerDto(String title, String content, String activityArea, String authTime, String contact, String endDate, Volunteer.Status status, Integer minParticipantCount, Integer maxParticipantCount){
        this.title = title;
        this.content = content;
        this.activityArea = activityArea;
        this.authTime = authTime;
        this.contact = contact;
        this.endDate = endDate;
        this.status = status;
        this.minParticipantCount = minParticipantCount;
        this.maxParticipantCount = maxParticipantCount;
    }

    public Volunteer toEntity(Member member){
        return Volunteer.builder()
                .title(title)
                .content(content)
                .activityArea(activityArea)
                .authTime(authTime)
                .contact(contact)
                .endDate(endDate)
                .minParticipantCount(minParticipantCount)
                .maxParticipantCount(maxParticipantCount)
                .member(member)
                .build();
    }

    @Getter
    public static class VolunteerListPage {

        private Integer totalCount;

        private Integer currentPageNumber;

        private Integer totalPageNumber;

        private List<VolunteerDto.ForPage> volunteersForPage;

        @Builder
        public VolunteerListPage(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<VolunteerDto.ForPage> volunteersForPage) {
            this.totalCount = totalCount;
            this.currentPageNumber = currentPageNumber;
            this.totalPageNumber = totalPageNumber;
            this.volunteersForPage = volunteersForPage;
        }
    }

    @Getter
    public static class ForPage {

        private Long seq;

        private Volunteer.Status status;

        private String title;

        private Integer maxParticipantCount;

        private String nickname;

        private Long memberSeq;

        private String endDate;

        private LocalDateTime createdDate;

        @QueryProjection
        public ForPage(Long seq, Volunteer.Status status, String title, Integer maxParticipantCount, String nickname, Long memberSeq, String endDate, LocalDateTime createdDate) {
            this.seq = seq;
            this.status = status;
            this.title = title;
            this.maxParticipantCount = maxParticipantCount;
            this.nickname = nickname;
            this.memberSeq = memberSeq;
            this.endDate = endDate;
            this.createdDate = createdDate;
        }
    }



    @Getter
    @ToString(of = {"seq", "title", "content", "authTime", "contact", "endDate", "activityArea", "minParticipantCount", "maxParticipantCount"})
    public static class Response {

        @Schema(name = "seq", title = "봉사활동 기본키", description = "봉사활동이 가지고 있는 고유 식별키입니다.")
        private final Long seq;

        @Schema(name = "title", title = "제목", description = "제목입니다.")
        private final String title;

        @Schema(name = "content", title = "내용", description = "내용입니다.")
        private final String content;

        @Schema(name = "activityArea", title = "활동지역", description = "활동지역입니다.")
        private final String activityArea;

        @Schema(name = "authTime", title = "인증시간", description = "인증시간입니다.")
        private final String authTime;

        @Schema(name = "contact", title = "연락처", description = "연락처입니다.")
        private final String contact;

        @Schema(name = "endDate", title = "종료일", description = "종료일입니다.")
        private final String endDate;

        @Schema(name = "minParticipantCount", title = "최소인원", description = "최소인원입니다.")
        private final Integer minParticipantCount;

        @Schema(name = "maxParticipantCount", title = "최대인원", description = "최대인원입니다.")
        private final Integer maxParticipantCount;

        @Builder
        public Response(Long seq, String title, String content, String activityArea, String authTime, String contact, String endDate, Integer minParticipantCount, Integer maxParticipantCount) {
            this.seq = seq;
            this.title = title;
            this.content = content;
            this.activityArea = activityArea;
            this.authTime = authTime;
            this.contact = contact;
            this.endDate = endDate;
            this.minParticipantCount = minParticipantCount;
            this.maxParticipantCount = maxParticipantCount;
        }
    }

//    @Getter
//    @ToString(of = {"memberSeq", "nickname", "title", "content", "status", "activityArea", "volunteerComment"})
//    public static class DetailResponse {
//
//        private final Long memberSeq;
//
//        private final String nickname;
//
//        private final String title;
//
//        private final String content;
//
//        private final Volunteer.Status status;
//
//        private final String activityArea;
//
//        private List<VolunteerServiceImpl.SimpleVolunteerCommentDto> volunteerComment;
//
//        public void setVolunteerComment(List<VolunteerServiceImpl.SimpleVolunteerCommentDto> volunteerComment){
//            this.volunteerComment = volunteerComment;
//        }
//
//        @Builder
//        public DetailResponse(Long memberSeq, String nickname, String title, String content, Volunteer.Status status, String activityArea, List<VolunteerComment> volunteerComment) {
//            this.memberSeq = memberSeq;
//            this.nickname = nickname;
//            this.title = title;
//            this.content = content;
//            this.status = status;
//            this.activityArea = activityArea;
//        }
//    }

    @Schema(name = "detail", title = "봉사활동 상세페이지 조회용 DTO", description = "봉사활동 게시글 상세페이지 조회용 DTO 입니다.")
    @Getter
    @ToString(of = {"writerSeq", "writerNickname", "writerProfileImagePath", "volunteerSeq", "title", "content", "activityArea", "status", "createdDate", "viewCount", "endDate", "authTime", "contact", "comments"})
    public static class Detail {

        @Schema(name = "writerSeq", title = "작성자 식별키", description = "작성자 식별키입니다.")
        private Long writerSeq;

        @Schema(name = "writerNickname", title = "작성자 닉네임", description = "작성자 닉네임입니다.")
        private String writerNickname;

        @Schema(name = "writerProfileImagePath", title = "작성자 프로필 이미지 경로", description = "작성자 프로필 이미지 경로입니다.")
        private String writerProfileImagePath;

        @Schema(name = "volunteerSeq", title = "봉사활동 게시글 식별키", description = "봉사활동 게시글 식별키입니다.")
        private Long volunteerSeq;

        @Schema(name = "title", title = "봉사활동 게시글 제목", description = "봉사활동 게시글 제목입니다.")
        private String title;

        @Schema(name = "content", title = "봉사활동 게시글 본문", description = "봉사활동 게시글 본문입니다.")
        private String content;

        @Schema(name = "activityArea", title = "봉사활동지역", description = "봉사활동 지역입니다.")
        private String activityArea;

        @Schema(name = "status", title = "봉사활동 모집상태", description = "봉사활동 모집상태입니다.")
        private Volunteer.Status status;

        @Schema(name = "createdDate", title = "봉사활동 게시글 작성일", description = "봉사활동 게시글 작성일입니다.")
        private LocalDate createdDate;

        @Schema(name = "viewCount", title = "조회수", description = "조회수입니다.")
        private Integer viewCount;

        @Schema(name = "endDate", title = "종료일", description = "종료일입니다.")
        private String endDate;

        @Schema(name = "authTime", title = "인증시간", description = "인증시간입니다.")
        private String authTime;

        @Schema(name = "contact", title = "연락처", description = "연락처입니다.")
        private String contact;

        @Schema(name = "approvedCount", title = "신청인원수", description = "신청인원수입니다.")
        private Integer approvedCount;

        @Schema(name = "comments", title = "댓글 목록", description = "댓글 목록입니다.")
        private List<VolunteerCommentDto.ForDetail> comments;

        @Builder
        public Detail(Long writerSeq, String writerNickname, String writerProfileImagePath, Long volunteerSeq, String title, String content, String activityArea, Volunteer.Status status, LocalDate createdDate, Integer viewCount, String endDate, String authTime, String contact, Integer approvedCount, List<VolunteerCommentDto.ForDetail> comments) {
            this.writerSeq = writerSeq;
            this.writerNickname = writerNickname;
            this.writerProfileImagePath = writerProfileImagePath;
            this.volunteerSeq = volunteerSeq;
            this.title = title;
            this.content = content;
            this.activityArea = activityArea;
            this.status = status;
            this.createdDate = createdDate;
            this.viewCount = viewCount;
            this.endDate = endDate;
            this.authTime = authTime;
            this.contact = contact;
            this.approvedCount = approvedCount;
            this.comments = comments;
        }

        public static VolunteerDto.Detail create(Volunteer volunteer, Member writer, Integer approvedCount, List<VolunteerCommentDto.ForDetail> commentsForDetail) {
            MemberDetail writerDetail = writer.getDetail();
            return Detail.builder()
                    .writerSeq(writer.getSeq())
                    .writerNickname(writerDetail.getNickname())
                    .writerProfileImagePath(Path.PROFILE_IMAGE_ACCESS_PATH + "/" + writerDetail.getProfileImageFilename())
                    .volunteerSeq(volunteer.getSeq())
                    .title(volunteer.getTitle())
                    .content(volunteer.getContent())
                    .activityArea(volunteer.getActivityArea())
                    .status(volunteer.getStatus())
                    .createdDate(volunteer.getCreatedDate().toLocalDate())
                    .viewCount((int) (long) volunteer.getViewCount())
                    .endDate(volunteer.getEndDate())
                    .authTime(volunteer.getAuthTime())
                    .contact(volunteer.getContact())
                    .approvedCount(approvedCount)
                    .comments(commentsForDetail)
                    .build();
        }
    }

    @Getter
    @ToString(of = {"content", "authenticatedParticipantSequences"})
    public static class VolunteerAuth {

        private String content;

        private List<Long> authenticatedParticipantSequences;

        @Builder
        public VolunteerAuth(String content, List<Long> authenticatedParticipantSequences) {
            this.content = content;
            this.authenticatedParticipantSequences = authenticatedParticipantSequences;
        }
    }

    @Getter
    @ToString(of = {"volunteerSeq", "content", "participants"})
    public static class VolunteerAuthDetail {

        private Long volunteerSeq;

        private String content;

        private List<Participant> participants;

        @Builder
        public VolunteerAuthDetail(Long volunteerSeq, String content, List<Participant> participants) {
            this.volunteerSeq = volunteerSeq;
            this.content = content;
            this.participants = participants;
        }

        @Getter
        @ToString(of = {"memberSeq", "nickname", "isApprove"})
        public static class Participant {

            private Long memberSeq;

            private String nickname;

            private boolean isApprove;

            @QueryProjection
            public Participant(Long memberSeq, String nickname, boolean isApprove) {
                this.memberSeq = memberSeq;
                this.nickname = nickname;
                this.isApprove = isApprove;
            }
        }
    }
}
