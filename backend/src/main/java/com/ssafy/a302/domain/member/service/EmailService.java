package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.service.dto.EmailAuthVerifyServiceDto;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.member.service.dto.PasswordResetServiceDto;

public interface EmailService {

    void sendEmailAuthKey(String email);

    void verifyEmail(EmailAuthVerifyServiceDto emailAuthVerifyServiceDto);

    boolean isExistsEmailAndAuthKey(String email, String authKey);

    void sendPasswordResetMail(String email);

    MemberDto.Response resetPassword(PasswordResetServiceDto toPasswordResetServiceDto);
}
