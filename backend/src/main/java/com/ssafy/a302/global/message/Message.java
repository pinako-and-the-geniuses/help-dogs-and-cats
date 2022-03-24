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

    public static String SUCCESS_MODIFY_MEMBER_PROFILE_IMAGE;

    public static String SUCCESS_REMOVE_MEMBER_PROFILE_IMAGE;

    public static String FAIL_LOGIN;

    public static String DUPLICATE_MEMBER_EMAIL;

    public static String DUPLICATE_MEMBER_NICKNAME;

    public static String DUPLICATE_MEMBER_TEL;

    public static String USABLE_MEMBER_EMAIL;

    public static String USABLE_MEMBER_NICKNAME;

    public static String USABLE_MEMBER_TEL;

    public static String PASSWORD_CONTAIN_MEMBER_EMAIL;

    public static String PASSWORD_CONTAIN_MEMBER_NICKNAME;

    public static String SUCCESS_AUTHENTICATE_EMAIL;

    public static String FAIL_AUTHENTICATE_EMAIL;

    public static String SUCCESS_SEND_EMAIL;

    public static String FAIL_SEND_EMAIL;

    public static String SUCCESS_RESET_PASSWORD;

    @Autowired
    public Message(MessageSource messageSource) {
        REGISTER_MEMBER = messageSource.getMessage("register.member", null, null);

        SUCCESS_LOGIN = messageSource.getMessage("success.login", null, null);
        SUCCESS_MODIFY = messageSource.getMessage("success.modify", null, null);
        SUCCESS_MODIFY_MEMBER = messageSource.getMessage("success.modify.member", null, null);
        SUCCESS_MODIFY_MEMBER_PROFILE_IMAGE = messageSource.getMessage("success.modify.member.profile-image", null, null);
        SUCCESS_RESET_PASSWORD = messageSource.getMessage("success.reset.password",null,null);

        FAIL_LOGIN = messageSource.getMessage("fail.login", null, null);

        DUPLICATE_MEMBER_EMAIL = messageSource.getMessage("duplicate.member.email", null, null);
        DUPLICATE_MEMBER_NICKNAME = messageSource.getMessage("duplicate.member.nickname", null, null);
        DUPLICATE_MEMBER_TEL = messageSource.getMessage("duplicate.member.tel", null, null);

        USABLE_MEMBER_EMAIL = messageSource.getMessage("usable.member.email", null, null);
        USABLE_MEMBER_NICKNAME = messageSource.getMessage("usable.member.nickname", null, null);
        USABLE_MEMBER_TEL = messageSource.getMessage("usable.member.tel", null, null);

        PASSWORD_CONTAIN_MEMBER_EMAIL = messageSource.getMessage("password-contain.member.email", null, null);
        PASSWORD_CONTAIN_MEMBER_NICKNAME = messageSource.getMessage("password-contain.member.nickname", null, null);

        SUCCESS_AUTHENTICATE_EMAIL = messageSource.getMessage("success.authenticate.email",null,null);
        FAIL_AUTHENTICATE_EMAIL = messageSource.getMessage("fail.authenticate.email",null,null);

        SUCCESS_SEND_EMAIL = messageSource.getMessage("success.send.email",null,null);
        FAIL_SEND_EMAIL = messageSource.getMessage("fail.send.email",null,null);

        SUCCESS_REMOVE_MEMBER_PROFILE_IMAGE = messageSource.getMessage("success.remove.member.profile-image", null, null);
    }
}
