package com.ssafy.a302hadoop.domain.hadoop.entity.enums;

public enum ProcessState {

    ADOPTED("입양"),
    NATDEATH("자연사"),
    EUTDEATH("안락사"),
    PROTECT("보호중"),
    EMISSION("방사");

    private final String description;

    ProcessState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}