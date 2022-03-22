package com.ssafy.a302.global.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Message {

    public static String REGISTER_MEMBER;

    public static String SUCCESS_LOGIN;

    public static String SUCCESS_MODIFY;

    public static String SUCCESS_MODIFY_MEMBER;

    public static String FAIL_LOGIN;

    public static String DUPLICATE_MEMBER_EMAIL;

    public static String DUPLICATE_MEMBER_NICKNAME;

    public static String DUPLICATE_MEMBER_TEL;

    public static String USABLE_MEMBER_EMAIL;

    public static String USABLE_MEMBER_NICKNAME;

    public static String PASSWORD_CONTAIN_MEMBER_EMAIL;

    public static String PASSWORD_CONTAIN_MEMBER_NICKNAME;

    @Autowired
    public Message(MessageSource messageSource) {
        REGISTER_MEMBER = messageSource.getMessage("register.member", null, null);

        SUCCESS_LOGIN = messageSource.getMessage("success.login", null, null);
        SUCCESS_MODIFY = messageSource.getMessage("success.modify", null, null);
        SUCCESS_MODIFY_MEMBER = messageSource.getMessage("success.modify.member", null, null);

        FAIL_LOGIN = messageSource.getMessage("fail.login", null, null);

        DUPLICATE_MEMBER_EMAIL = messageSource.getMessage("duplicate.member.email", null, null);
        DUPLICATE_MEMBER_NICKNAME = messageSource.getMessage("duplicate.member.nickname", null, null);
        DUPLICATE_MEMBER_TEL = messageSource.getMessage("duplicate.member.tel", null, null);

        USABLE_MEMBER_EMAIL = messageSource.getMessage("usable.member.email", null, null);
        USABLE_MEMBER_NICKNAME = messageSource.getMessage("usable.member.nickname", null, null);

        PASSWORD_CONTAIN_MEMBER_EMAIL = messageSource.getMessage("password-contain.member.email", null, null);
        PASSWORD_CONTAIN_MEMBER_NICKNAME = messageSource.getMessage("password-contain.member.nickname", null, null);
    }
}
