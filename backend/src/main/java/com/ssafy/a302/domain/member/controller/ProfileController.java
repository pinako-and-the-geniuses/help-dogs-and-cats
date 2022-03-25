package com.ssafy.a302.domain.member.controller;

import com.ssafy.a302.domain.member.service.ProfileService;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
@Tag(name = "회원 프로필", description = "회원 프로필 관련 REST API가 작성됩니다.")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "프로필 조회 API",
            description = "회원 식별키 전달받고 회원가입을 진행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 조회에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{memberSeq}")
    public BaseResponseDto<MemberDto.Profile> profileView(@PathVariable(name = "memberSeq") Long memberSeq) {

        MemberDto.Profile memberProfile = profileService.getProfile(memberSeq);

        return BaseResponseDto.<MemberDto.Profile>builder()
                .message(Message.SUCCESS)
                .data(memberProfile)
                .build();
    }
}
