package com.ssafy.a302.global.constant;

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

    public static String PATTERN_BLANK;

    public static String PATTERN_CATEGORY;

    public static String PATTERN_DATE;

    public static String NULL_MEMBER;

    public static String NULL_MEMBER_EMAIL;

    public static String NULL_EMAIL_AUTH;

    public static String INVALID_FILE_EXT;

    public static String INVALID_MEMBER_SEQ;

    public static String BAD_REQUEST;

    public static String FORBIDDEN;

    public static String UNAVAILABLE;

    public static String ERROR;

    public static String INVALID_VOLUNTEER;

    public static String INVALID_VOLUNTEER_PARTICIPANT;

    public static String INVALID_VOLUNTEER_CANCEL_PARTICIPANT;

    public static String INVALID_VOLUNTEER_CREATOR_PARTICIPANT;

    public static String INVALID_VOLUNTEER_COMMENT;

    public static String INVALID_VOLUNTEER_CREATOR_COMMENT;

    public static String INVALID_VOLUNTEER_CREATOR;

    public static String INVALID_VOLUNTEER_AUTH;

    public static String INVALID_ADOPT_AUTH;

    public static String INVALID_BADGE_APPROVE;

    @Autowired
    public ErrorMessage(MessageSource messageSource) {
        AUTHENTICATION_MEMBER = messageSource.getMessage("authentication.member", null, null);

        PATTERN = messageSource.getMessage("pattern", null, null);
        PATTERN_MEMBER_EMAIL = messageSource.getMessage("pattern.member.email", null, null);
        PATTERN_MEMBER_PASSWORD = messageSource.getMessage("pattern.member.password", null, null);
        PATTERN_MEMBER_NICKNAME = messageSource.getMessage("pattern.member.nickname", null, null);
        PATTERN_MEMBER_TEL = messageSource.getMessage("pattern.member.tel", null, null);
        PATTERN_BLANK = messageSource.getMessage("pattern.blank", null, null);
        PATTERN_CATEGORY = messageSource.getMessage("pattern.category", null, null);
        PATTERN_DATE = messageSource.getMessage("pattern.date", null, null);

        NULL_MEMBER = messageSource.getMessage("null.member", null, null);
        NULL_MEMBER_EMAIL = messageSource.getMessage("null.member.email", null, null);
        NULL_EMAIL_AUTH = messageSource.getMessage("null.email.auth",null,null);

        INVALID_FILE_EXT = messageSource.getMessage("invalid.file.ext", null, null);
        INVALID_MEMBER_SEQ = messageSource.getMessage("invalid.member.seq", null, null);

        BAD_REQUEST = messageSource.getMessage("bad-request", null, null);

        FORBIDDEN = messageSource.getMessage("forbidden", null, null);

        UNAVAILABLE = messageSource.getMessage("unavailable", null, null);

        ERROR = messageSource.getMessage("error", null, null);

        INVALID_VOLUNTEER = messageSource.getMessage("invalid.volunteer", null, null);

        INVALID_VOLUNTEER_PARTICIPANT = messageSource.getMessage("invalid.volunteer.participant", null, null);
        INVALID_VOLUNTEER_CANCEL_PARTICIPANT = messageSource.getMessage("invalid.volunteer.cancel.participant", null, null);
        INVALID_VOLUNTEER_CREATOR_PARTICIPANT = messageSource.getMessage("invalid.volunteer.creator.participant", null, null);

        INVALID_VOLUNTEER_COMMENT = messageSource.getMessage("invalid.volunteer.comment", null, null);
        INVALID_VOLUNTEER_CREATOR_COMMENT = messageSource.getMessage("invalid.volunteer.creator.comment", null, null);
        INVALID_VOLUNTEER_CREATOR = messageSource.getMessage("invalid.volunteer.creator", null, null);

        INVALID_VOLUNTEER_AUTH = messageSource.getMessage("invalid.volunteer.auth", null, null);
        INVALID_ADOPT_AUTH = messageSource.getMessage("invalid.adopt.auth", null, null);

        INVALID_BADGE_APPROVE = messageSource.getMessage("invalid.badge.approve", null, null);
    }
}
