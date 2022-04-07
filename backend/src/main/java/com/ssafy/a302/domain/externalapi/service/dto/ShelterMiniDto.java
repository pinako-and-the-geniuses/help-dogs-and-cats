package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"code", "name"})
public class ShelterMiniDto {

    private final String code;

    private final String name;

    @Builder
    public ShelterMiniDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
