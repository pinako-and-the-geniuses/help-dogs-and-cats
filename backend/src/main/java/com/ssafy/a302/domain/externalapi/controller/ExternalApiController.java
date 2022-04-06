package com.ssafy.a302.domain.externalapi.controller;

import com.ssafy.a302.domain.externalapi.service.ExternalApiService;
import com.ssafy.a302.domain.externalapi.service.dto.SidoDto;
import com.ssafy.a302.global.constant.Message;
import com.ssafy.a302.global.dto.BaseResponseDto;
import com.ssafy.a302.global.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/external-api")
@RestController
@Tag(name = "외부 API", description = "외부 API 관련 REST API가 작성됩니다.")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @Operation(
            summary = "시도 조회 API",
            description = "시도 데이터를 반환합니다.",
            tags = {""}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "시도 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sido")
    public BaseResponseDto<List<SidoDto>> sido() throws IOException, ParseException {
        List<SidoDto> sidos = externalApiService.getSido();

        return BaseResponseDto.<List<SidoDto>>builder()
                .message(Message.SUCCESS)
                .data(sidos)
                .build();
    }
}
