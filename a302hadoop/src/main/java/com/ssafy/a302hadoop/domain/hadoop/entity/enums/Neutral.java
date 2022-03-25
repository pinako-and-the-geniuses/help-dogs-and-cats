package com.ssafy.a302hadoop.domain.hadoop.entity.enums;

public enum Neutral {
    YES("중성화 됨"),
    NO("중성화 안됨"),
    UNF("확인 불가");

    private final String description;

    Neutral(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}