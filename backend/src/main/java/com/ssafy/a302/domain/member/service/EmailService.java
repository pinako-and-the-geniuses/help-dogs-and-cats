package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.EmailAuth;
import com.ssafy.a302.domain.member.service.dto.EmailAuthVerifyServiceDto;

public interface EmailService {

    void sendEmailAuthKey(String email);

    void verifyEmail(EmailAuthVerifyServiceDto emailAuthVerifyServiceDto);

    boolean isExistsEmailAndAuthKey(String email, String authKey);

    void sendPasswordResetMail(String email);
}
