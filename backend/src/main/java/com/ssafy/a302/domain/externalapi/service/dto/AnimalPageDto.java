package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(of = {"totalCount", "currentPageNumber", "totalPageNumber", "animalDtos"})
public class AnimalPageDto {

    private final Integer totalCount;

    private final Integer currentPageNumber;

    private final Integer totalPageNumber;

    private final List<animalDto> animalDtos;

    @Builder
    public AnimalPageDto(Integer totalCount, Integer currentPageNumber, Integer totalPageNumber, List<animalDto> animalDtos) {
        this.totalCount = totalCount;
        this.currentPageNumber = currentPageNumber;
        this.totalPageNumber = totalPageNumber;
        this.animalDtos = animalDtos;
    }
}
