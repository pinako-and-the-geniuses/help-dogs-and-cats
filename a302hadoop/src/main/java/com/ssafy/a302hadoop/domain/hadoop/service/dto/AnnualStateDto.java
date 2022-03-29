package com.ssafy.a302hadoop.domain.hadoop.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualState;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AnnualStateDto {

    private int year;

    private AnimalData.ProcessState state;

    private String species;

    private int count;

    @QueryProjection
    public AnnualStateDto(int year, AnimalData.ProcessState state, int count, String species) {
        this.year = year;
        this.state = state;
        this.count = count;
        this.species = species;
    }


    public AnnualState toEntity() {
        return AnnualState.builder()
                .year(year)
                .state(state)
                .species(species)
                .count(count)
                .build();
    }
}
