package com.ssafy.a302.domain.member.controller;

import com.ssafy.a302.domain.member.controller.dto.MemberRequestDto;
import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import com.ssafy.a302.global.util.JwtTokenUtil;
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

import java.util.HashMap;
import java.util.Map;

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

    @Operation(
            summary = "닉네임 중복 확인 API",
            description = "전달받은 닉네임이 데이터베이스에 존재하는지 확인하고 결과를 반환합니다.",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "nickname",
                            description = "닉네임",
                            examples = {
                                    @ExampleObject(name = "good1", summary = "good1", value = "good", description = "영문자, 최소 길이 만족하므로 형식 검증 통과"),
                                    @ExampleObject(name = "good2", summary = "good2", value = "닉네임1", description = "한글/숫자 사용, 최소 길이 만족하므로 형식 검증 통과"),
                                    @ExampleObject(name = "bad1", summary = "bad1", value = "bad", description = "최소길이 4자 미만이므로 형식 검증 실패"),
                                    @ExampleObject(name = "bad2", summary = "bad2", value = "ㅎㅇㅎㅇ", description = "한글 자음만 사용하였으므로 형식 검증 실패"),
                                    @ExampleObject(name = "bad3", summary = "bad3", value = "ㅗㅗㅗㅗ", description = "한글 모음만 사용하였으므로 형식 검증 실패"),
                                    @ExampleObject(name = "bad4", summary = "bad4", value = "01234567890", description = "최대 길이 10자 초과이므로 형식 검증 실패")
                            },
                            required = true)
            },
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "중복된 닉네임입니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "204",
                    description = "사용할 수 있는 닉네임입니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "닉네임 형식 검증에 실패하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/nickname-duplicate-check/{nickname}")
    public ResponseEntity<BaseResponseDto<?>> nicknameDuplicateCheck(@PathVariable(name = "nickname") String nickname) {
        String nicknameRegx = "^[0-9|a-z|가-힣|\\s]{4,10}$";
        if (!nickname.matches(nicknameRegx)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_MEMBER_NICKNAME);
        }

        HttpStatus status = null;
        if (memberService.isExistsNickname(nickname)) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.builder()
                .message(status == HttpStatus.OK ? Message.DUPLICATE_MEMBER_NICKNAME : Message.USABLE_MEMBER_NICKNAME)
                .build(), status);
    }

    @Operation(
            summary = "로그인 API",
            description = "아이디, 패스워드를 전달받고 로그인을 진행합니다. 로그인에 성공하면 JWT 토큰을 반환합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "204",
                    description = "패스워드를 잘못 입력하셨습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "이메일 형식 검증에 실패하였거나, 회원 데이터가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto<?>> login(@Validated @RequestBody MemberRequestDto.LoginInfo loginInfo) {
        HttpStatus status = null;
        Map<String, Object> data = null;

        if (memberService.login(loginInfo.toServiceDto())) {
            status = HttpStatus.OK;

            data = new HashMap<>();
            MemberDto.LoginResponse memberDto = memberService.getMemberLoginResponseDto(loginInfo.getEmail());
            String jwtToken = JwtTokenUtil.getToken(memberDto.getEmail());

            data.put("jwtToken", jwtToken);
            data.put("memberInfo", memberDto);
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.builder()
                .message(status == HttpStatus.OK ? Message.SUCCESS_LOGIN : Message.FAIL_LOGIN)
                .data(data)
                .build(), status);
    }
}
