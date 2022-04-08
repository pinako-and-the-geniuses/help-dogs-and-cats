package com.ssafy.a302.domain.member.exception;

import com.ssafy.a302.global.exception.DuplicateException;

public class DuplicateTelException extends DuplicateException {

    public DuplicateTelException(String message) {
        super(message);
    }
}
