package com.ssafy.a302hadoop.global.exception.handler;

import com.ssafy.a302hadoop.global.entity.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler(Exception.class)
        public ErrorResponseDto<?> exceptionHandler(Exception e) {
                logPrint(e);
                return ErrorResponseDto.builder()
                        .message("서버에 문제가 발생하였습니다.")
                        .build();
        }

        private void logPrint(Exception e) {
                log.error("[exceptionHandler] ex", e);
        }

}
