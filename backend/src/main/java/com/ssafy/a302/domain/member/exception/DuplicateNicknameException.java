package com.ssafy.a302.domain.member.exception;

import com.ssafy.a302.global.exception.DuplicateException;

public class DuplicateNicknameException extends DuplicateException {

    public DuplicateNicknameException(String message) {
        super(message);
    }
}
