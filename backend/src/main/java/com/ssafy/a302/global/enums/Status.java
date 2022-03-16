package com.ssafy.a302.global.enums;

public enum Status {

    REQUEST("인증 요청"),
    REJECT("인증 반려"),
    DONE("인증 완료");

    private final String description;

    Status (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
