package com.ssafy.a302.domain.image.controller;

import com.ssafy.a302.domain.image.service.ImageService;
import com.ssafy.a302.global.constant.ErrorMessage;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
@Tag(name = "이미지", description = "이미지 관련 REST API가 작성됩니다.")
public class ImageController {

    private final ImageService imageService;

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @Operation(
            summary = "이미지 저장 API",
            description = "이미지 파일을 전달받고 이미지를 저장합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "이미지를 저장하였습니다.",
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<Map<String, String>> saveImageFile(@RequestPart MultipartFile imageFile) throws IOException {
        log.info("이미지 파일 = {}", imageFile);

        String extRegx = "(.*?)\\.(png|jpeg|gif|jpg)$";
        String originalFilename = imageFile.getOriginalFilename();
        if (!originalFilename.matches(extRegx)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_EXT);
        }

        String imageFilePath = imageService.saveImageFile(imageFile);

        Map<String, String> data = new HashMap<>();
        data.put("imageFilePath", imageFilePath);

        return BaseResponseDto.<Map<String, String>>builder()
                .message(Message.SUCCESS_SAVE_FILE)
                .data(data)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @Operation(
            summary = "이미지 삭제 API",
            description = "이미지 파일이름을 전달받고 이미지를 저장합니다.",
            tags = {"member"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "이미지를 삭제하였습니다.",
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{imageFilename}")
    public BaseResponseDto<?> removeImageFile(@PathVariable(name = "imageFilename") String imageFilename) throws IOException {
        log.info("이미지 파일 이름 = {}", imageFilename);

        imageService.removeImageFile(imageFilename);

        return BaseResponseDto.builder()
                .message(Message.REMOVE)
                .build();
    }
}
