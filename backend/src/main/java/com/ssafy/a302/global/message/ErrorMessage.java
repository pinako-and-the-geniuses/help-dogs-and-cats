package com.ssafy.a302.global.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessage {

    public static String AUTHENTICATION_MEMBER;

    public static String PATTERN;

    public static String PATTERN_MEMBER_EMAIL;

    public static String PATTERN_MEMBER_PASSWORD;

    public static String PATTERN_MEMBER_NICKNAME;

    public static String PATTERN_MEMBER_TEL;

    public static String NULL_MEMBER;

    public static String NULL_MEMBER_EMAIL;

    public static String NULL_EMAIL_AUTH;

    public static String INVALID_MEMBER_SEQ;

    public static String FORBIDDEN;

    public static String ERROR;

    @Autowired
    public ErrorMessage(MessageSource messageSource) {
        AUTHENTICATION_MEMBER = messageSource.getMessage("authentication.member", null, null);

        PATTERN = messageSource.getMessage("pattern", null, null);
        PATTERN_MEMBER_EMAIL = messageSource.getMessage("pattern.member.email", null, null);
        PATTERN_MEMBER_PASSWORD = messageSource.getMessage("pattern.member.password", null, null);
        PATTERN_MEMBER_NICKNAME = messageSource.getMessage("pattern.member.nickname", null, null);
        PATTERN_MEMBER_TEL = messageSource.getMessage("pattern.member.tel", null, null);

        NULL_MEMBER = messageSource.getMessage("null.member", null, null);
        NULL_MEMBER_EMAIL = messageSource.getMessage("null.member.email", null, null);
        NULL_EMAIL_AUTH = messageSource.getMessage("null.email.auth",null,null);

        INVALID_MEMBER_SEQ = messageSource.getMessage("invalid.member.seq", null, null);

        FORBIDDEN = messageSource.getMessage("forbidden", null, null);

        ERROR = messageSource.getMessage("error", null, null);
    }
}
