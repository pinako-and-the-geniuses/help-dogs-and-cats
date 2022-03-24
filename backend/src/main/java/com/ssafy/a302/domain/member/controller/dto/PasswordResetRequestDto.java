package com.ssafy.a302.domain.member.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Schema(name = "비밀번호 재설정 DTO", description = "비밀번호 재설정 시 새 비밀번호와 jwtToken이 담긴 DTO입니다.")
@Getter
@NoArgsConstructor
public class PasswordResetRequestDto {

    @Schema(name = "newPassword", title = "새 패스워드", description = "새로운 패스워드입니다.", example = "newpwd!!", minLength = 8, maxLength = 20, required = true)
    @Pattern(message = "{pattern.member.password}", regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$")
    private String newPassword;

    @Builder
    public PasswordResetRequestDto(String newPassword) {
        this.newPassword = newPassword;
    }

}
