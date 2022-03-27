package com.ssafy.a302.domain.volunteer.controller.dto;

import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerCommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Schema(name = "봉사활동 댓글 요청 DTO", description = "봉사활동 댓글 API 호출 시 사용되는 요청 DTO 입니다.")
@Getter
public class VolunteerCommentRequestDto {

    @Schema(name = "봉사활동 댓글 요청 DTO", description = "봉사활동 댓글 등록 API 호출 시 사용되는 요청 DTO 입니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"content", "parentSeq"})
    public static class RegisterInfo{
        // 봉사활동 사진 추가해야함
        private String content;

        private Long parentSeq;



        @Builder
        public RegisterInfo(String content, Long parentSeq){
            this.content = content;
            this.parentSeq = parentSeq;

        }

        public VolunteerCommentDto toServiceDto(){
            return VolunteerCommentDto.builder()
                    .content(content)
                    .parentSeq(parentSeq)
                    .build();
        }
    }
}
