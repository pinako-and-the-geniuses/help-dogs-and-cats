package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"sidoCode", "sigunguCode", "name"})
public class SigunguDto {

    private final String sidoCode;

    private final String sigunguCode;

    private final String name;

    @Builder
    public SigunguDto(String sidoCode, String sigunguCode, String name) {
        this.sidoCode = sidoCode;
        this.sigunguCode = sigunguCode;
        this.name = name;
    }
}
