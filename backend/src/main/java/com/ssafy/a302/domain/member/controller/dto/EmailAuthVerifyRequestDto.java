package com.ssafy.a302.domain.member.controller.dto;

import com.ssafy.a302.domain.member.service.dto.EmailAuthVerifyServiceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class EmailAuthVerifyRequestDto {
    @Schema(name = "email", title = "이메일", description = "이메일입니다.")
    @Pattern(message = "{pattern.member.email}", regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$")
    private String email;

    @Schema(name = "authKey", title = "인증키", description = "사용자가 입력한 인증키입니다.")
    private String authKey;

    @Builder
    public EmailAuthVerifyRequestDto(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }

    public EmailAuthVerifyServiceDto toEmailAuthVerifyServiceDto() {
        return EmailAuthVerifyServiceDto
                .builder()
                .email(email)
                .authKey(authKey)
                .build();
    }

}
