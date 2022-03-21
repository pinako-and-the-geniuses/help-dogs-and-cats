package com.ssafy.a302.domain.member.controller;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
@Tag(name = "회원", description = "회원 데이터 관련 REST API가 작성됩니다.")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원가입 API",
            description = "이메일, 패스워드, 닉네임, 핸드폰 번호, 활동 지역을 전달받고 회원가입을 진행합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "이메일, 패스워드, 닉네임, 핸드폰 번호 중 한 가지 이상 형식 검증에 실패하였거나, 패스워드에 이메일 또는 닉네임이 포함됩니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "이메일 또는 닉네임이 중복됩니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<?> register(@Validated @RequestBody MemberRequestDto.RegisterInfo registerInfo) {
        memberService.register(registerInfo.toServiceDto());

        return BaseResponseDto.builder()
                .message(Message.REGISTER_MEMBER)
                .build();
    }

    @Operation(
            summary = "이메일 중복 확인 API",
            description = "전달받은 이메일이 데이터베이스에 존재하는지 확인하고 결과를 반환합니다.",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "email",
                            description = "이메일",
                            examples = {
                                    @ExampleObject(name = "good1", summary = "good1", value = "good@good.com", description = "형식 검증 통과"),
                                    @ExampleObject(name = "good2", summary = "good2", value = "hello.world@hello.com", description = "형식 검증 통과"),
                                    @ExampleObject(name = "bad1", summary = "bad1", value = "bad", description = "형식 검증 실패"),
                                    @ExampleObject(name = "bad2", summary = "bad2", value = "bad@", description = "형식 검증 실패"),
                                    @ExampleObject(name = "bad3", summary = "bad3", value = "bad@bad", description = "형식 검증 실패"),
                                    @ExampleObject(name = "bad4", summary = "bad4", value = "bad@.com", description = "형식 검증 실패")
                            },
                            required = true)
            },
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "중복된 이메일입니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "204",
                    description = "사용할 수 있는 이메일입니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "이메일 형식 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/email-duplicate-check/{email}")
    public ResponseEntity<BaseResponseDto<?>> emailDuplicateCheck(@PathVariable(name = "email") String email) {
        String emailRegx = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$";
        if (!email.matches(emailRegx)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_MEMBER_EMAIL);
        }

        HttpStatus status = null;
        if (memberService.isExistsEmail(email)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.builder()
                .message(status == HttpStatus.OK ? Message.DUPLICATE_MEMBER_EMAIL : Message.USABLE_MEMBER_EMAIL)
                .build(), status);
    }
}
