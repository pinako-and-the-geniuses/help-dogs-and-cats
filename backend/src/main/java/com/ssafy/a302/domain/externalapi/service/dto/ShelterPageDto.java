package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "shelterDtos"})
public class ShelterPageDto {

    private final Integer totalCount;

    private final Integer currentPageNumber;

    private final Integer totalPageNumber;

    private final List<ShelterDto> shelterDtos;

    @Builder
    public ShelterPageDto(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<ShelterDto> shelterDtos) {
        this.totalCount = totalCount;
        this.currentPageNumber = currentPageNumber;
        this.totalPageNumber = totalPageNumber;
        this.shelterDtos = shelterDtos;
    }
}
