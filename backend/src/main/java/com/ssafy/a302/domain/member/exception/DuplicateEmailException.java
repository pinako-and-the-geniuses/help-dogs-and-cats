package com.ssafy.a302.domain.member.exception;

import com.ssafy.a302.global.exception.DuplicateException;

public class DuplicateEmailException extends DuplicateException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
