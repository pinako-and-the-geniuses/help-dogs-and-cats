package com.ssafy.a302.global.exception.handler;

import com.ssafy.a302.global.dto.ErrorResponseDto;
import com.ssafy.a302.global.exception.DuplicateException;
import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.constant.Message;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MailException.class)
    public ErrorResponseDto<?> mailExceptionHandler(MailException e){
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(Message.FAIL_SEND_EMAIL)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDto<?> httpMessageNotReadableExceptionExceptionHandler(HttpMessageNotReadableException e) {
        logPrint(e);

        return ErrorResponseDto.builder()
                .message(ErrorMessage.PATTERN)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponseDto<?> methodArgumentTypeMismatchExceptionExceptionHandler(MethodArgumentTypeMismatchException e) {
        logPrint(e);

        return ErrorResponseDto.builder()
                .message(ErrorMessage.PATTERN)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logPrint(e);

        Map<String, Object> data = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", fieldError.getField());
            errorInfo.put("message", fieldError.getDefaultMessage());
            data.put("fieldError", errorInfo);
        }

        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", globalError.getObjectName());
            errorInfo.put("message", globalError.getDefaultMessage());
            data.put("globalError", errorInfo);
        }

        return ErrorResponseDto.<Map<String, Object>>builder()
                .message(ErrorMessage.PATTERN)
                .data(data)
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseDto<?> accessDeniedExceptionHandler(AccessDeniedException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(ErrorMessage.FORBIDDEN)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateException.class)
    public ErrorResponseDto<?> duplicationExceptionHandler(DuplicateException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(IOException.class)
    public ErrorResponseDto<?> iOExceptionHandler(IOException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDto<?> noSuchElementExceptionHandler(NoSuchElementException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(ErrorMessage.UNAVAILABLE)
                .build();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto<Void> parseExceptionHandler(ParseException e) {
        logPrint(e);
        return ErrorResponseDto.<Void>builder()
                .message(ErrorMessage.UNAVAILABLE)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto<?> exceptionHandler(Exception e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .message(ErrorMessage.ERROR)
                .build();
    }

    private void logPrint(Exception e) {
        log.error("[exceptionHandler] ex", e);
    }
}
