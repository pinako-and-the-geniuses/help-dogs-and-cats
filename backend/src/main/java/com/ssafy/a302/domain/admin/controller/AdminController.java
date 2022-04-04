package com.ssafy.a302.domain.admin.controller;

import com.ssafy.a302.domain.admin.controller.dto.AdoptAuthRequestDto;
import com.ssafy.a302.domain.admin.controller.dto.VolunteerAuthRequestDto;
import com.ssafy.a302.domain.admin.service.AdminService;
import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.service.AdoptService;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.badge.service.BadgeService;
import com.ssafy.a302.domain.volunteer.service.VolunteerCommentService;
import com.ssafy.a302.domain.volunteer.service.VolunteerParticipantService;
import com.ssafy.a302.domain.volunteer.service.VolunteerService;
import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.constant.Message;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.enums.Status;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@RestController
@Tag(name = "관리자", description = "관리자 기능 관련 REST API가 작성됩니다.")
public class AdminController {

    private final VolunteerService volunteerService;

    private final VolunteerCommentService volunteerCommentService;

    private final AdminService adminService;

    private final BadgeService badgeService;

    private final AdoptService adoptService;

    private final VolunteerParticipantService volunteerParticipantService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 봉사활동 인증 상세 페이지 조회 API",
            description = "봉사활동 식별키를 전달받고 봉사활동 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/volunteers/auth/{volunteerSeq}")
    public BaseResponseDto<?> volunteerAuthDetail(@PathVariable Long volunteerSeq) {
        log.info("봉사활동 식별키 = {}", volunteerSeq);

        VolunteerAuthDto.Response findVolunteerAuth = adminService.volunteerAuthDetail(volunteerSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_ADMIN_VOLUNTEER_AUTH)
                .data(findVolunteerAuth)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 봉사 인증 조치 API",
            description = "인증 상태, 봉사활동 식별키를 전달받고 봉사 인증 요청을 처리합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/volunteers/auth/{volunteerSeq}")
    public BaseResponseDto<?> changeVolunteerAuthStatus(@RequestBody VolunteerAuthRequestDto.StatusInfo statusInfo,
                                                        @PathVariable Long volunteerSeq) {
        log.info("상태 정보 = {}", statusInfo);
        log.info("봉사활동 식별키 = {}", volunteerSeq);

        adminService.changeVolunteerAuthStatus(statusInfo.toServiceDto(), volunteerSeq);

        if (Status.valueOf(statusInfo.getStatus().toUpperCase()) == Status.DONE) {
            List<Long> volunteerParticipantSeqList = volunteerParticipantService.getParticipantSeqALlByVolunteerSeqAndApproveEqTrue(volunteerSeq);
            for (Long memberSeq : volunteerParticipantSeqList) {
                if (badgeService.isQualifiedBraveStepBadge(memberSeq)) {
                    /**
                     * 용기있는 발 딛음 뱃지 체크
                     */
                    badgeService.approveBraveStepBadge(memberSeq);
                } else if (badgeService.isQualifiedVolunteerParticipationKingBadge(memberSeq)) {
                    /**
                     * 나는 봉사 참여왕 뱃지 체크
                     */
                    badgeService.approveVolunteerParticipationKingBadge(memberSeq);
                } else if (badgeService.isQualifiedVolunteerParticipationKing2Badge(memberSeq)) {
                    /**
                     * 나는 봉사 참여왕2 모집 체크
                     */
                    badgeService.approveVolunteerParticipationKing2Badge(memberSeq);
                }
            }

            Long memberSeq = volunteerService.getMemberByVolunteerSeq(volunteerSeq).getSeq();
            if (badgeService.isQualifiedVolunteerRecruitmentKingBadge(memberSeq)) {
                /**
                 * 나는 봉사 모집왕 뱃지 체크
                 */
                badgeService.approveVolunteerRecruitmentKingBadge(memberSeq);

            } else if (badgeService.isQualifiedVolunteerRecruitmentKing2Badge(memberSeq)) {
                /**
                 * 나는 봉사 모집왕2 뱃지 체크
                 */
                badgeService.approveVolunteerRecruitmentKing2Badge(memberSeq);
            }
        }

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_ADMIN_CHANGE_VOLUNTEER_AUTH_STATUS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 입양 인증 상세페이지 조회 API",
            description = "입양 인증 식별키를 전달받고 입양 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/adopts/auth/{adoptSeq}")
    public BaseResponseDto<?> adoptAuthDetail(@PathVariable Long adoptSeq) {
        log.info("입양 인증 식별키 = {}", adoptSeq);

        AdoptAuthDto.Response findAdoptAuth = adminService.adoptAuthDetail(adoptSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_ADMIN_ADOPT_AUTH)
                .data(findAdoptAuth)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 입양 인증 조치 API",
            description = "인증 상태, 입양 인증 식별키를 전달받고 입양 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/adopts/auth/{adoptSeq}")
    public BaseResponseDto<?> changeAdoptAuthStatus(@RequestBody AdoptAuthRequestDto.StatusInfo statusInfo,
                                                    @PathVariable Long adoptSeq) {
        log.info("상태 정보 = {}", statusInfo);
        log.info("입양 인증 식별키 = {}", adoptSeq);

        adminService.changeAdoptAuthStatus(statusInfo.toServiceDto(), adoptSeq);

        if (Status.valueOf(statusInfo.getStatus().toUpperCase()) == Status.DONE) {
            Long memberSeq = adoptService.getMemberByAdoptSeq(adoptSeq).getSeq();

            if (badgeService.isQualifiedStartOfHappinessBadge(memberSeq)) {
                /**
                 * 행복의 시작 뱃지 체크
                 */
                badgeService.approveStartOfHappinessBadge(memberSeq);
            } else if (badgeService.isQualifiedHappinessIsSquareBadge(memberSeq)) {
                /**
                 * 행복은 제곱 뱃지 체크
                 */
                badgeService.approveHappinessIsSquareBadge(memberSeq);
            }
        }

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_ADMIN_CHANGE_ADOPT_AUTH_STATUS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 봉사활동 인증 목록 조회 API",
            description = "인증 상태, 봉사활동 인증 식별키 전달받고 봉사활동 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/volunteers/auth")
    public BaseResponseDto<VolunteerAuthDto.VolunteerAuthPage> getVolunteerAuthList(Pageable pageable, @RequestParam String search) {
        log.info("페이징 정보 = {}", pageable);
        log.info("검색 구분 = {}", search);

        if (StringUtils.hasText(search)) {
            if (!search.equals("all") && !search.equals("auth") && !search.equals("not-auth")) {
                throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
            }

            VolunteerAuthDto.VolunteerAuthPage volunteerAuthPage = adminService.getVolunteerAuthList(pageable, search);

            return BaseResponseDto.<VolunteerAuthDto.VolunteerAuthPage>builder()
                    .message(Message.SUCCESS)
                    .data(volunteerAuthPage)
                    .build();
        } else {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_BLANK);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "괸리자 입양 인증 목록 조회 API",
            description = "인증 상태, 입양 인증 식별키 전달받고 입양 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API 요청에 성공하였습니다.",
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/adopts/auth")
    public BaseResponseDto<AdoptAuthDto.AdoptAuthPage> getAdoptAuthList(Pageable pageable, @RequestParam String search) {
        log.info("페이징 정보 = {}", pageable);
        log.info("검색 구분 = {}", search);

        if (StringUtils.hasText(search)) {
            if (!search.equals("all") && !search.equals("auth") && !search.equals("not-auth")) {
                throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
            }

            AdoptAuthDto.AdoptAuthPage adoptAuthPage = adminService.getAdoptAuthList(pageable, search);

            return BaseResponseDto.<AdoptAuthDto.AdoptAuthPage>builder()
                    .message(Message.SUCCESS)
                    .data(adoptAuthPage)
                    .build();
        } else {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_BLANK);
        }
    }
}
