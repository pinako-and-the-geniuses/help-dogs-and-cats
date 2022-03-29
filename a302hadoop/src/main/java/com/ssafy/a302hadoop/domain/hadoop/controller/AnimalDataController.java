package com.ssafy.a302hadoop.domain.hadoop.controller;


import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import com.ssafy.a302hadoop.domain.hadoop.service.HadoopService;
import com.ssafy.a302hadoop.global.entity.BaseResponseDto;
import com.ssafy.a302hadoop.global.entity.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hadoop/v1")
@RestController
@Tag(name = "하둡 데이터 컨트롤러", description = "분석 통계 관련 REST API가 작성됩니다.")
public class AnimalDataController {

    private final HadoopService hadoopService;

    @Operation(
            summary = "연도별 유기동물 품종 마릿수 API",
            description = "2017년도 부터 작년까지의 유기동물 품종 별 마릿수 데이터를 제공합니다.",
            tags = {"AnnualBreed"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "데이터 조회에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/annual-breed")
    public BaseResponseDto<?> getAnnualBreedData(){
        Map<Integer,Map<String, Object>> myMap = hadoopService.getAnnualBreedData(LocalDate.now());

        return BaseResponseDto.builder()
                .data(myMap)
                .message("조회에 성공하였습니다.")
                .build();
    }

    @Operation(
            summary = "연도별 유기동물 처리현황, 마릿수 API",
            description = "2017년도 부터 작년까지의 유기동물 연도별 처리현황과 마릿수 데이터를 제공합니다.",
            tags = {"AnnualState"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "데이터 조회에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/annual-state")
    public BaseResponseDto<?> getAnnualStateData(){
        Map<Integer,Map<String, List<AnimalDataResponseDto.AnnualStateInfo>>> myMap = hadoopService.getAnnualStateData(LocalDate.now());

        return BaseResponseDto.builder()
                .data(myMap)
                .message("조회에 성공하였습니다.")
                .build();
    }

    @Operation(
            summary = "연도별 유기동물 나이대별 처리현황 마릿수 API",
            description = "2017년도 부터 작년까지의 유기동물 나이대별 처리현황과 마릿수 데이터를 제공합니다.",
            tags = {"AnnualBreed"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "데이터 조회에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/age-state")
    public BaseResponseDto<?> getAgeStateData(){
        Map<Integer,Map<String, Map<Integer,List<AnimalDataResponseDto.AgeStateInfo>>>> myMap = hadoopService.getAgeStateData(LocalDate.now());

        return BaseResponseDto.builder()
                .data(myMap)
                .message("조회에 성공하였습니다.")
                .build();
    }


    @Operation(
            summary = "연도별 유기동물 동물 종류별 중성화 여부, 마릿수 API",
            description = "2017년도 부터 작년까지의 유기동물 동물별 중성화 여부와, 마릿수 데이터를 제공합니다.",
            tags = {"AnnualBreed"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "데이터 조회에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/species-neutral")
    public BaseResponseDto<?> getSpeciesNeutralData(){
        Map<Integer,Map<String, Object>> myMap = hadoopService.getSpeciesNeutralData(LocalDate.now());

        return BaseResponseDto.builder()
                .data(myMap)
                .message("조회에 성공하였습니다.")
                .build();
    }



}
