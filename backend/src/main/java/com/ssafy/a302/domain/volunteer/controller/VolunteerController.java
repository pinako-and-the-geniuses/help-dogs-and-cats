package com.ssafy.a302.domain.volunteer.controller;

import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerAuthRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerCommentRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerParticipantRequestDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
import com.ssafy.a302.domain.volunteer.service.VolunteerCommentService;
import com.ssafy.a302.domain.volunteer.service.VolunteerParticipantService;
import com.ssafy.a302.domain.volunteer.service.VolunteerService;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.auth.CustomUserDetails;
import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.constant.Message;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/volunteers")
@RestController
@Tag(name = "봉사활동", description = "봉사활동 데이터 관련 REST API가 작성됩니다.")
public class VolunteerController {

    private final VolunteerService volunteerService;

    private final VolunteerParticipantService volunteerParticipantService;

    private final VolunteerCommentService volunteerCommentService;

    private final AuthenticationUtil authenticationUtil;

    @Operation(
            summary = "봉사활동 API",
            description = "제목, 내용, 활동지역, 봉사인증시간, 멤버(최대인원, 최소인원), 연락처, 종료일을 전달받고 봉사활동 진행합니다.",
            tags = {"volunteer"}
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
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<?> register(@Validated @RequestBody VolunteerRequestDto.RegisterInfo registerInfo, Authentication authentication) {
        log.info("봉사활동 등록 정보 = {}", registerInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        volunteerService.register(registerInfo.toServiceDto(), memberSeq);

        return BaseResponseDto.builder()
                .message(Message.REGISTER_VOLUNTEER)
                .build();
    }

    // 봉사활동 목록 조회
    @Operation(
            summary = "봉사활동 목록 조회/검색 API",
            description = "페이징 정보, 마감일, 지역, 봉사 시간 인정 여부, 제목 검색어를 전달받고 봉사활동 목록을 조회니다.",
            tags = {"volunteer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "봉사활동 목록을 조회하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseResponseDto<VolunteerDto.VolunteerListPage> viewPage(Pageable pageable,
                                                                    @RequestParam String keyword,
                                                                    @RequestParam String endDate,
                                                                    @RequestParam(defaultValue = "false") boolean admit,
                                                                    @RequestParam(defaultValue = "전체") String activityArea) {
        log.info("페이징 정보 = {}", pageable);
        log.info("모집 종료일 = {}", endDate);
        log.info("봉사 시간 인정 여부 = {}", admit);
        log.info("활동 지역 = {}", activityArea);
        log.info("검색어 = {}", keyword);

        String dateRegx = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?";
        if (!endDate.matches(dateRegx)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_DATE);
        }

        VolunteerDto.SearchInfo searchInfo = VolunteerDto.SearchInfo.builder()
                .endDate(LocalDate.parse(endDate))
                .admit(admit)
                .activityArea(activityArea)
                .keyword(keyword)
                .build();

        VolunteerDto.VolunteerListPage volunteerListPage = volunteerService.getPage(pageable, searchInfo);

        return BaseResponseDto.<VolunteerDto.VolunteerListPage>builder()
                .message(Message.SUCCESS_VIEWPAGE_VOLUNTEER)
                .data(volunteerListPage)
                .build();
    }

    @Operation(
            summary = "봉사활동 상세페이지 조회 API",
            description = "봉사활동 게시글 식별키, 회원 식별키를 전달받고 봉사활동 게시글을 조회합니다.",
            tags = {"volunteer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "봉사활동 게시글을 조회하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 봉사활동 게시글입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "토큰 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerSeq}")
    public BaseResponseDto<VolunteerDto.Detail> volunteerDetail(@PathVariable Long volunteerSeq) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);

        VolunteerDto.Detail detail = volunteerService.volunteerDetail(volunteerSeq);

        return BaseResponseDto.<VolunteerDto.Detail>builder()
                .message(Message.SUCCESS_VOLUNTEER_DETAIL_LIST)
                .data(detail)
                .build();
    }

    // 봉사활동 상세페이지 수정
    @Operation(
            summary = "봉사활동 수정 API",
            description = "종료일, 제목, 활동지역, 인증시간, 모집인원, 신청여부, 연락처, 작성자 닉네임, 작성자seq, 내용, 첨부파일을 전달받고 봉사활동을 수정합니다.",
            tags = {"volunteer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "봉사활동 수정에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{volunteerSeq}")
    public BaseResponseDto<?> updateVolunteerDetail(@Validated @RequestBody VolunteerRequestDto.UpdateInfo updateInfo,
                                                    @PathVariable Long volunteerSeq,
                                                    Authentication authentication) {
        log.info("봉사활동 게시글 수정 정보 = {}", updateInfo);
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        
        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        volunteerService.updateVolunteerDetail(updateInfo.toServiceDto(), volunteerSeq, memberSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_UPDATE_VOLUNTEER)
                .build();

    }

    // 봉사활동 진행상태 수정
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{volunteerSeq}/status")
    public BaseResponseDto<?> changeVolunteerStatus(@Validated @RequestBody VolunteerRequestDto.StatusInfo statusInfo, 
                                                    @PathVariable Long volunteerSeq, Authentication authentication) {
        log.info("상태 정보 = {}", statusInfo);
        
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerService.changeVolunteerStatus(statusInfo.toServiceDto(), volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CHANGE_STATUS_VOLUNTEER)
                .build();

    }


    @Operation(
            summary = "봉사활동 삭제 API",
            description = "봉사활동 게시글 식별키, 회원 식별키를 전달받고 봉사활동을 삭제합니다.",
            tags = {"community"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "봉사활동을 삭제하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 봉사활동입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{volunteerSeq}")
    public BaseResponseDto<?> deleteVolunteer(@PathVariable Long volunteerSeq,
                                              Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        
        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        volunteerService.remove(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER)
                .build();

    }

    // 봉사활동 신청
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{volunteerSeq}/apply")
    public BaseResponseDto<?> applyVolunteer(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerParticipantService.applyVolunteer(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_APPLY_VOLUNTEER)
                .build();
    }

    // 봉사활동 취소 => 만약에 개설자가 봉사활동을 취소한다면?
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/apply")
    public BaseResponseDto<?> cancelVolunteer(@PathVariable Long volunteerSeq, Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        volunteerParticipantService.cancelVolunteer(volunteerSeq, memberSeq);
        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CANCEL_VOLUNTEER)
                .build();
    }

    // 봉사활동 참여자 목록 조회
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerSeq}/participants")
    public BaseResponseDto<?> getVolunteerParticipantList(@Validated @PathVariable Long volunteerSeq, Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_VOLUNTEER_PARTICIPANT_LIST)
                .data(volunteerParticipantService.getVolunteerParticipantList(volunteerSeq, memberSeq))
                .build();
    }

    // 봉사활동 참여자 참석여부 수정
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{volunteerSeq}/participants/{memberSeq}")
    public BaseResponseDto<?> changeParticipantIsApprove(@Validated @PathVariable Long volunteerSeq, 
                                                         @PathVariable Long memberSeq, 
                                                         @RequestBody VolunteerParticipantRequestDto.IsApproveInfo isApproveInfo, 
                                                         Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("회원 식별키 = {}", memberSeq);
        log.info("참석 여부 = {}", isApproveInfo);
        
        Long memberCreatorSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerParticipantService.changeParticipantIsApprove(isApproveInfo.toServiceDto(), volunteerSeq, memberSeq, memberCreatorSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_CHANGE_PARTICIPANT_APPROVE)
                .build();
    }

    // 봉사활동 참여자 참석여부 삭제
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/participants/{memberSeq}")
    public BaseResponseDto<?> deleteVolunteerParticipant(@Validated @PathVariable Long volunteerSeq, 
                                                         @PathVariable Long memberSeq, 
                                                         Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("회원 식별키 = {}", memberSeq);
        
        Long memberCreatorSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerParticipantService.deleteVolunteerParticipant(volunteerSeq, memberSeq, memberCreatorSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER_PARTICIPANT)
                .build();
    }
    
    // 봉사활동 댓글 작성
    @Operation(
            summary = "봉사활동 게시글 댓글 등록 API",
            description = "봉사활동 게시글 식별키, 댓글 본문, 부모 댓글 식별키를 전달받고 봉사활동 게시글 댓글을 등록합니다.",
            tags = {"community"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "봉사활동 게시글 댓글을 등록하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 봉사활동 게시글입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{volunteerSeq}/comments")
    public BaseResponseDto<?> registerVolunteerComment(@PathVariable Long volunteerSeq,
                                                       @RequestBody VolunteerCommentRequestDto.RegisterInfo registerInfo,
                                                       Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("봉사활동 게시글 댓글 등록 정보 = {}", registerInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);

        volunteerCommentService.registerVolunteerComment(volunteerSeq, memberSeq, registerInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_REGISTER_VOLUNTEER_COMMENT)
                .build();
    }

    // 봉사활동 댓글 삭제
    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "봉사활동 게시글 댓글 삭제 API",
            description = "봉사활동 게시글 식별키, 댓글 식별키를 전달받고 봉사활동 게시글 댓글을 삭제합니다.",
            tags = {"community"}
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerSeq}/comments/{commentsSeq}")
    public BaseResponseDto<?> deleteVolunteerComment(@Validated @PathVariable Long volunteerSeq, 
                                                     @PathVariable Long commentsSeq, 
                                                     Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("봉사활동 게시글 댓글 식별키 = {}", commentsSeq);
        
        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        volunteerCommentService.deleteVolunteerComment(volunteerSeq, commentsSeq, memberSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_DELETE_VOLUNTEER_COMMENT)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "봉사활동 인증 요청 API",
            description = "봉사활동 게시글 식별키, 봉사활동 인증 내용, 인증 인원의 식별키를 전달받고 입양 인증 요청을 수행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "봉사활동 인증 요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "토큰 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{volunteerSeq}/auth")
    public BaseResponseDto<?> requestVolunteerAuth(@PathVariable(name = "volunteerSeq") Long volunteerSeq,
                                                   @Validated @RequestBody VolunteerAuthRequestDto.RequestInfo requestInfo,
                                                   Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("봉사활동 인증 요청 정보 = {}", requestInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        volunteerService.requestVolunteerAuth(memberSeq, volunteerSeq, requestInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "봉사활동 인증 수정 API",
            description = "봉사활동 게시글 식별키, 봉사활동 인증 내용, 인증 인원의 식별키를 전달받고 입양 인증 요청을 수정합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "봉사활동 인증 수정 요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "토큰 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{volunteerSeq}/auth")
    public BaseResponseDto<?> modifyVolunteerAuth(@PathVariable(name = "volunteerSeq") Long volunteerSeq,
                                                  @Validated @RequestBody VolunteerAuthRequestDto.ModifyInfo modifyInfo,
                                                  Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);
        log.info("봉사활동 인증 수정 정보 = {}", modifyInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        volunteerService.modifyVolunteerAuth(memberSeq, volunteerSeq, modifyInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "봉사활동 인증 조회 API",
            description = "봉사활동 게시글 식별키를 전달받고 입양 인증 조회를 수정합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "봉사활동 인증 조회 요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "토큰 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{volunteerSeq}/auth")
    public BaseResponseDto<VolunteerDto.VolunteerAuthDetail> modifyVolunteerAuth(@PathVariable(name = "volunteerSeq") Long volunteerSeq,
                                                                                 Authentication authentication) {
        log.info("봉사활동 게시글 식별키 = {}", volunteerSeq);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        VolunteerDto.VolunteerAuthDetail volunteerAuthDetail = volunteerService.getVolunteerAuth(memberSeq, volunteerSeq);

        return BaseResponseDto.<VolunteerDto.VolunteerAuthDetail>builder()
                .message(Message.SUCCESS)
                .data(volunteerAuthDetail)
                .build();
    }
}
