package com.ssafy.a302.domain.member.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmailAuthVerifyServiceDto {
    @Schema(name = "email", title = "이메일", description = "이메일입니다.")
    private String email;

    @Schema(name = "authKey", title = "인증키", description = "사용자가 입력한 인증키입니다.")
    private String authKey;

    @Builder
    public EmailAuthVerifyServiceDto(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }
}
