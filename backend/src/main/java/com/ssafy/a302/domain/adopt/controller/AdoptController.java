package com.ssafy.a302.domain.adopt.controller;

import com.ssafy.a302.domain.adopt.controller.dto.AdoptAuthRequestDto;
import com.ssafy.a302.domain.adopt.service.AdoptService;
import com.ssafy.a302.domain.adopt.service.dto.AdoptDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/adopts")
@RestController
@Tag(name = "입양", description = "입양 관련 REST API가 작성됩니다.")
public class AdoptController {

    private final AdoptService adoptService;

    private final AuthenticationUtil authenticationUtil;

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "입양 인증 요청 API",
            description = "회원 식별키, 입양 인증 제목, 입양 인증 내용을 전달받고 입양 인증 요청을 수행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "입양 인증 요청에 성공하였습니다.",
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
    @PostMapping("/auth")
    public BaseResponseDto<?> requestAdoptAuth(@Validated @RequestBody AdoptAuthRequestDto.RequestAdoptAuthInfo requestAdoptAuthInfo,
                                               Authentication authentication) {
        log.info("입양 인증 요청 정보 = {}", requestAdoptAuthInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        adoptService.requestAdoptAuth(memberSeq, requestAdoptAuthInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "입양 인증 수정 API",
            description = "회원 식별키, 입양 인증 식별키, 입양 인증 제목, 입양 인증 내용을 전달받고 입양 인증 수정을 수행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "입양 인증 수정에 성공하였습니다.",
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
    @PutMapping("/auth/{adoptAuthSeq}")
    public BaseResponseDto<?> requestAdoptAuth(@PathVariable(name = "adoptAuthSeq") Long adoptAuthSeq,
                                               @Validated @RequestBody AdoptAuthRequestDto.ModifyAdoptAuthInfo modifyAdoptAuthInfo,
                                               Authentication authentication) {
        log.info("입양 인증 식별키 = {}", adoptAuthSeq);
        log.info("입양 인증 수정 정보 = {}", modifyAdoptAuthInfo);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        adoptService.modifyAdoptAuth(memberSeq, adoptAuthSeq, modifyAdoptAuthInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.SUCCESS)
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
    @Operation(
            summary = "입양 인증 조회 API",
            description = "회원 식별키, 입양 인증 식별키를 전달받고 입양 인증 데이터를 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "입양 인증 조회에 성공하였습니다.",
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
    @GetMapping("/auth/{adoptAuthSeq}")
    public BaseResponseDto<AdoptDto.AdoptAuth> requestAdoptAuth(@PathVariable(name = "adoptAuthSeq") Long adoptAuthSeq,
                                               Authentication authentication) {
        log.info("입양 인증 식별키 = {}", adoptAuthSeq);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        AdoptDto.AdoptAuth adoptAuth = adoptService.getAdoptAuth(memberSeq, adoptAuthSeq);

        return BaseResponseDto.<AdoptDto.AdoptAuth>builder()
                .message(Message.SUCCESS)
                .data(adoptAuth)
                .build();
    }
}
