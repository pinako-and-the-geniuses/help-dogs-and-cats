package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"sidoCode", "name"})
public class SidoDto {

    private final String sidoCode;

    private final String name;

    @Builder
    public SidoDto(String sidoCode, String name) {
        this.sidoCode = sidoCode;
        this.name = name;
    }
}
