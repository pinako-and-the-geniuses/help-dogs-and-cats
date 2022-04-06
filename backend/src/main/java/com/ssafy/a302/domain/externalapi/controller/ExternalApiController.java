package com.ssafy.a302.domain.externalapi.controller;

import com.ssafy.a302.domain.externalapi.service.ExternalApiService;
import com.ssafy.a302.domain.externalapi.service.dto.ShelterDto;
import com.ssafy.a302.domain.externalapi.service.dto.ShelterPageDto;
import com.ssafy.a302.domain.externalapi.service.dto.SidoDto;
import com.ssafy.a302.domain.externalapi.service.dto.SigunguDto;
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
import org.springframework.data.domain.Pageable;
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
            tags = {"external-api"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "시도 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sido")
    public BaseResponseDto<List<SidoDto>> sido() throws IOException, ParseException {
        List<SidoDto> sidoDtos = externalApiService.getSidoDtos();

        return BaseResponseDto.<List<SidoDto>>builder()
                .message(Message.SUCCESS)
                .data(sidoDtos)
                .build();
    }

    @Operation(
            summary = "시군구 조회 API",
            description = "시군구 데이터를 반환합니다.",
            tags = {"external-api"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "시군구 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sigungu/{sidoCode}")
    public BaseResponseDto<List<SigunguDto>> sigungu(@PathVariable String sidoCode) throws IOException, ParseException {
        List<SigunguDto> sigunguDtos = externalApiService.getSigunguDtos(sidoCode);

        return BaseResponseDto.<List<SigunguDto>>builder()
                .message(Message.SUCCESS)
                .data(sigunguDtos)
                .build();
    }

    @Operation(
            summary = "보호소 전체 조회 API",
            description = "보호소 전체 데이터를 페이징 처리 후에 반환합니다.",
            tags = {"external-api"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "보호소 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/shelters")
    public BaseResponseDto<ShelterPageDto> shelters(Pageable pageable) throws IOException, ParseException {
        ShelterPageDto shelterPageDto = externalApiService.getShelterPageDto(pageable);

        return BaseResponseDto.<ShelterPageDto>builder()
                .message(Message.SUCCESS)
                .data(shelterPageDto)
                .build();
    }

    @Operation(
            summary = "보호소 일부 조회 API",
            description = "보호소 일부 데이터를 페이징 처리 후에 반환합니다.",
            tags = {"external-api"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "보호소 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/shelters/part")
    public BaseResponseDto<ShelterPageDto> shelterPart(Pageable pageable,
                                                       @RequestParam String sidoCode,
                                                       @RequestParam String sigunguCode) throws IOException, ParseException {
        ShelterPageDto shelterPageDto = externalApiService.getShelterPageDto(pageable, sidoCode, sigunguCode);

        return BaseResponseDto.<ShelterPageDto>builder()
                .message(Message.SUCCESS)
                .data(shelterPageDto)
                .build();
    }

    @Operation(
            summary = "보호소 상세 조회 API",
            description = "보호소 상세 데이터를 반환합니다.",
            tags = {"external-api"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "보호소 데이터를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "503",
                    description = "요청을 수행할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/shelters/{shelterName}")
    public BaseResponseDto<ShelterDto> shelter(@PathVariable String shelterName) throws IOException, ParseException {
        ShelterDto shelterDto = externalApiService.getShelterDto(shelterName);

        return BaseResponseDto.<ShelterDto>builder()
                .message(Message.SUCCESS)
                .data(shelterDto)
                .build();
    }
}
