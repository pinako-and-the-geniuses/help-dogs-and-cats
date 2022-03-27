package com.ssafy.a302.domain.community.controller;

import com.ssafy.a302.domain.community.controller.dto.CommunityRequestDto;
import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.service.CommunityService;
import com.ssafy.a302.domain.community.service.dto.CommunityDto;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
@RestController
@Tag(name = "커뮤니티", description = "커뮤니티 관련 REST API가 작성됩니다.")
public class CommunityController {

    private final CommunityService communityService;

    private final AuthenticationUtil authenticationUtil;

    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @Operation(
            summary = "커뮤니티 등록 API",
            description = "제목, 본문, 카데고리를 전달받고 커뮤니티 게시글을 등록합니다.",
            tags = {"community"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "커뮤니티 게시글을 등록하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "제목, 본문, 카테고리 중 한 가지 이상 형식 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "토큰 검증에 실패하였습니다.",
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<?> register(@Validated @RequestBody CommunityRequestDto.RegisterInfo registerInfo, Authentication authentication) {
        Long memberSeq = authenticationUtil.getMemberSeq(authentication);
        communityService.register(registerInfo.toServiceDto(), memberSeq);

        return BaseResponseDto.builder()
                .message(Message.REGISTER_COMMUNITY_ARTICLE)
                .build();
    }

    @Operation(
            summary = "커뮤니티 목록 조회/검색 API",
            description = "페이지 번호, 게시글 개수, 검색 구분, 검색어, 카테고리를 전달받고 커뮤니티 게시글을 조회합니다.",
            tags = {"community"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "커뮤니티 게시글 데이터를 반환하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseResponseDto<CommunityDto.CommunityListPage> viewPage(Pageable pageable,
                                       @RequestParam String category,
                                       @RequestParam String search,
                                       @RequestParam String keyword) {

        Community.Category communityCategory = null;
        if (StringUtils.hasText(category)) {
            try {
                communityCategory = Community.Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
            }
        }

        CommunityDto.CommunityListPage communityListPage = communityService.getPage(pageable, communityCategory, search, keyword);

        return BaseResponseDto.<CommunityDto.CommunityListPage>builder()
                .message(Message.SUCCESS)
                .data(communityListPage)
                .build();
    }
}
