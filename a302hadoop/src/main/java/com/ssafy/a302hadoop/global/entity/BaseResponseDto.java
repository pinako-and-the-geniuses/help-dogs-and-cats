package com.ssafy.a302hadoop.global.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(name = "기본 응답 DTO", description = "API 호출이 성공하면 사용되는 DTO 입니다.")
public class BaseResponseDto<T> {

    @Schema(name = "message", title = "메시지", description = "결과 메시지입니다.", defaultValue = "null", nullable = true)
    private final String message;

    @Schema(name = "data", title = "데이터", description = "결과 데이터입니다.", defaultValue = "null", nullable = true)
    private final T data;

    @Builder
    public BaseResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
