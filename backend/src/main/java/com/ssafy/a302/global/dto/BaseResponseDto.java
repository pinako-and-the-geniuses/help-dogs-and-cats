package com.ssafy.a302.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponseDto {
    
    private String message;

    private Object data;

    @Builder
    public BaseResponseDto(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
