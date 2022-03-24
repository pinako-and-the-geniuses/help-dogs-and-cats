package com.ssafy.a302.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class PasswordResetServiceDto {

    @Pattern(message = "{pattern.member.password}", regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$")
    private String newPassword;

    private Long memberSeq;

    @Builder
    public PasswordResetServiceDto(String newPassword, Long memberSeq) {
        this.newPassword = newPassword;
        this.memberSeq = memberSeq;
    }
}
