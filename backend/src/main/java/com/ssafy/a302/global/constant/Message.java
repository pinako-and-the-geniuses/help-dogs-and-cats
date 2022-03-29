package com.ssafy.a302.global.constant;

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

    public static String SUCCESS_FIND_EMAIL;

    public static String SUCCESS_SAVE_FILE;

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

    public static String REGISTER_COMMUNITY_ARTICLE;

    public static String SUCCESS_SEND_EMAIL;

    public static String FAIL_SEND_EMAIL;

    public static String SUCCESS_RESET_PASSWORD;

    public static String REGISTER_VOLUNTEER;

    public static String SUCCESS_DELETE_VOLUNTEER;

    public static String SUCCESS_APPLY_VOLUNTEER;

    public static String SUCCESS_CANCEL_VOLUNTEER;

    public static String SUCCESS_CHANGE_STATUS_VOLUNTEER;

    public static String SUCCESS_VOLUNTEER_PARTICIPANT_LIST;

    public static String SUCCESS_CHANGE_PARTICIPANT_APPROVE;

    public static String SUCCESS_DELETE_VOLUNTEER_PARTICIPANT;

    public static String SUCCESS_REGISTER_VOLUNTEER_COMMENT;

    public static String SUCCESS_DELETE_VOLUNTEER_COMMENT;

    public static String SUCCESS_VIEWPAGE_VOLUNTEER;

    public static String SUCCESS_UPDATE_VOLUNTEER;

    public static String SUCCESS_VOLUNTEER_DETAIL_LIST;

    public static String SUCCESS_ADMIN_VOLUNTEER_AUTH_LIST;

    public static String SUCCESS_ADMIN_VOLUNTEER_AUTH;

    public static String SUCCESS_ADMIN_CHANGE_VOLUNTEER_AUTH_STATUS;

    public static String SUCCESS_ADMIN_ADOPT_AUTH;

    public static String REMOVE;

    public static String SUCCESS;






    @Autowired
    public Message(MessageSource messageSource) {
        REGISTER_MEMBER = messageSource.getMessage("register.member", null, null);

        SUCCESS_LOGIN = messageSource.getMessage("success.login", null, null);
        SUCCESS_MODIFY = messageSource.getMessage("success.modify", null, null);
        SUCCESS_MODIFY_MEMBER = messageSource.getMessage("success.modify.member", null, null);
        SUCCESS_MODIFY_MEMBER_PROFILE_IMAGE = messageSource.getMessage("success.modify.member.profile-image", null, null);
        SUCCESS_RESET_PASSWORD = messageSource.getMessage("success.reset.password",null,null);
        SUCCESS_FIND_EMAIL = messageSource.getMessage("success.find.email", null, null);
        SUCCESS_SAVE_FILE = messageSource.getMessage("success.save.file", null, null);

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

        REGISTER_VOLUNTEER = messageSource.getMessage("register.volunteer", null, null);

        SUCCESS_DELETE_VOLUNTEER = messageSource.getMessage("success.delete.volunteer", null, null);
        SUCCESS_CHANGE_STATUS_VOLUNTEER = messageSource.getMessage("success.change-status.volunteer", null, null);

        SUCCESS_APPLY_VOLUNTEER = messageSource.getMessage("success.apply.volunteer", null, null);
        SUCCESS_CANCEL_VOLUNTEER = messageSource.getMessage("success.cancel.volunteer", null, null);

        REGISTER_COMMUNITY_ARTICLE = messageSource.getMessage("register.community.article", null, null);

        SUCCESS_VOLUNTEER_PARTICIPANT_LIST = messageSource.getMessage("success.volunteer.participant.list", null, null);
        SUCCESS_CHANGE_PARTICIPANT_APPROVE = messageSource.getMessage("success.change.participant.approve", null, null);
        SUCCESS_DELETE_VOLUNTEER_PARTICIPANT = messageSource.getMessage("success.delete.volunteer.participant", null, null);
        SUCCESS_REGISTER_VOLUNTEER_COMMENT = messageSource.getMessage("success.register.volunteer.comment", null, null);
        SUCCESS_DELETE_VOLUNTEER_COMMENT = messageSource.getMessage("success.delete.volunteer.comment", null, null);
        SUCCESS_VOLUNTEER_DETAIL_LIST = messageSource.getMessage("success.volunteer.detail.list", null, null);

        SUCCESS_UPDATE_VOLUNTEER = messageSource.getMessage("success.update.volunteer", null, null);
        SUCCESS_VIEWPAGE_VOLUNTEER = messageSource.getMessage("success.viewpage.volunteer", null, null);


        SUCCESS_ADMIN_VOLUNTEER_AUTH_LIST = messageSource.getMessage("success.admin.volunteer.auth.list", null, null);
        SUCCESS_ADMIN_VOLUNTEER_AUTH = messageSource.getMessage("success.admin.volunteer.auth", null, null);
        SUCCESS_ADMIN_CHANGE_VOLUNTEER_AUTH_STATUS = messageSource.getMessage("success.admin.change.volunteer.auth.status", null, null);
        SUCCESS_ADMIN_ADOPT_AUTH = messageSource.getMessage("success.admin.adopt.auth", null, null);


        REMOVE = messageSource.getMessage("remove", null, null);
        SUCCESS = messageSource.getMessage("success", null, null);
    }
}