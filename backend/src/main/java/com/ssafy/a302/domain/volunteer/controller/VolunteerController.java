package com.ssafy.a302.domain.volunteer.controller;

import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerCommentRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerParticipantRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.service.VolunteerCommentService;
import com.ssafy.a302.domain.volunteer.service.VolunteerParticipantService;
import com.ssafy.a302.domain.volunteer.service.VolunteerService;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.domain.volunteer.service.VolunteerServiceImpl;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.auth.CustomUserDetails;
import com.ssafy.a302.global.constant.Message;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/volunteers")
@RestController
@Tag(name = "봉사활동", description = "봉사활동 데이터 관련 REST API가 작성됩니다.")
public class VolunteerController {

    private final VolunteerService volunteerService;

    private final VolunteerParticipantService volunteerParticipantService;

    private final VolunteerCommentService volunteerCommentService;

    // 봉사활동 생성
    @Operation(
            summary = "봉사활동 API",
            description = "제목, 내용, 활동지역, 봉사인증시간, 멤버(최대인원, 최소인원), 연락처, 종료일을 전달받고 봉사활동 진행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "봉사활동 등록에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<?> register(@Validated @RequestBody VolunteerRequestDto.RegisterInfo registerInfo, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerService.register(registerInfo.toServiceDto(), memberSeq);

        return BaseResponseDto.builder()
                .message(Message.REGISTER_VOLUNTEER)
                .build();
    }

    // 봉사활동 목록 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseResponseDto<VolunteerDto.VolunteerListPage> viewPage(Pageable pageable,
                                                  @RequestParam String keyword) {

        VolunteerDto.VolunteerListPage volunteerListPage = volunteerService.getPage(pageable, keyword);
        return BaseResponseDto.<VolunteerDto.VolunteerListPage>builder()
                .message(Message.SUCCESS_VIEWPAGE_VOLUNTEER)
                .data(volunteerListPage)
                .build();

    }

    // 봉사활동 상세페이지 조회
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerSeq}")
    public BaseResponseDto<?> volunteerDetail(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        VolunteerDto.DetailResponse findVolunteerDetail = volunteerService.volunteerDetail(volunteerSeq, memberSeq);

//        findVolunteerDetail.setVolunteerComment(result);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_VOLUNTEER_DETAIL_LIST)
                .data(findVolunteerDetail)
                .build();

    }


    // 봉사활동 상세페이지 수정
//    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/{volunteerSeq}")
//    public BaseResponseDto<?> updateVolunteerDetail(@Validated @RequestBody VolunteerRequestDto.UpdateInfo updateInfo,
//                                                    @PathVariable Long volunteerSeq,
//                                                    Authentication authentication) {
//        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
//        volunteerService.updateVolunteerDetail(updateInfo.toServiceDto(), volunteerSeq, memberSeq);
//
//        return BaseResponseDto.builder()
//                .message(Message.SUCCESS_UPDATE_VOLUNTEER)
//                .build();
//
//    }

    // 봉사활동 진행상태 수정
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{volunteerSeq}/status")
    public BaseResponseDto<?> changeVolunteerStatus(@Validated @RequestBody VolunteerRequestDto.StatusInfo statusInfo, @PathVariable Long volunteerSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerService.changeVolunteerStatus(statusInfo.toServiceDto(), volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CHANGE_STATUS_VOLUNTEER)
                .build();

    }


    // 봉사활동 삭제
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}")
    public BaseResponseDto<?> deleteVolunteer(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerService.deleteVolunteer(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER)
                .build();

    }

    // 봉사활동 신청
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{volunteerSeq}/apply")
    public BaseResponseDto<?> applyVolunteer(@Validated @PathVariable Long volunteerSeq, Authentication authentication){
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerParticipantService.applyVolunteer(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_APPLY_VOLUNTEER)
                .build();
    }

    // 봉사활동 취소 => 만약에 개설자가 봉사활동을 취소한다면?
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/apply")
    public BaseResponseDto<?> cancelVolunteer(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerParticipantService.cancelVolunteer(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CANCEL_VOLUNTEER)
                .build();
    }

    // 봉사활동 참여자 목록 조회
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerSeq}/participants")
    public BaseResponseDto<?> getVolunteerParticipantList(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_VOLUNTEER_PARTICIPANT_LIST)
                .data(volunteerParticipantService.getVolunteerParticipantList(volunteerSeq, memberSeq))
                .build();
    }

    // 봉사활동 참여자 참석여부 수정
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{volunteerSeq}/participants/{memberSeq}")
    public BaseResponseDto<?> changeParticipantIsApprove(@Validated @PathVariable Long volunteerSeq, @PathVariable Long memberSeq, @RequestBody VolunteerParticipantRequestDto.IsApproveInfo isApproveInfo, Authentication authentication) {
        Long memberCreatorSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerParticipantService.changeParticipantIsApprove(isApproveInfo.toServiceDto(), volunteerSeq, memberSeq, memberCreatorSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CHANGE_PARTICIPANT_APPROVE)
                .build();
    }

    // 봉사활동 참여자 참석여부 삭제
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/participants/{memberSeq}")
    public BaseResponseDto<?> deleteVolunteerParticipant(@Validated @PathVariable Long volunteerSeq, @PathVariable Long memberSeq, Authentication authentication) {
        Long memberCreatorSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerParticipantService.deleteVolunteerParticipant(volunteerSeq, memberSeq, memberCreatorSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER_PARTICIPANT)
                .build();
    }


    // 봉사활동 댓글 작성
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{volunteerSeq}/comments")
    public BaseResponseDto<?> registerVolunteerComment(@Validated @PathVariable Long volunteerSeq, @RequestBody VolunteerCommentRequestDto.RegisterInfo registerInfo, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerCommentService.registerVolunteerComment(volunteerSeq, memberSeq, registerInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_REGISTER_VOLUNTEER_COMMENT)
                .build();
    }

    // 봉사활동 댓글 삭제
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/comments/{commentsSeq}")
    public BaseResponseDto<?> deleteVolunteerComment(@Validated @PathVariable Long volunteerSeq, @PathVariable Long commentsSeq, Authentication authentication) {
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerCommentService.deleteVolunteerComment(volunteerSeq, commentsSeq, memberSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER_COMMENT)
                .build();
    }

}
