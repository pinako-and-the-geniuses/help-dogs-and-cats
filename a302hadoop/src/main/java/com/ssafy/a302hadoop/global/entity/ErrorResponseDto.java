package com.ssafy.a302hadoop.global.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(name = "에러 응답 DTO", description = "API 호출이 실패하면 사용되는 DTO 입니다.")
public class ErrorResponseDto<T> {

    @Schema(name = "message", title = "에러 메시지", description = "결과 메시지입니다.", defaultValue = "null", nullable = true)
    private final String message;

    @Schema(name = "data", title = "데이터", description = "결과 데이터입니다.", defaultValue = "null", nullable = true)
    private final T data;

    @Builder
    public ErrorResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
