package com.ssafy.a302.global.exception.handler;

import com.ssafy.a302.global.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponseDto illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return BaseResponseDto.builder()
                .message(e.getMessage())
                .data(null)
                .build();
    }
}
