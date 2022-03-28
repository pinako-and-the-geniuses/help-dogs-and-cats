package com.ssafy.a302hadoop.domain.hadoop.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import com.ssafy.a302hadoop.domain.hadoop.entity.SpeciesNeutral;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SpeciesNeutralDto {

    private int year;

    private AnimalData.Neutral neutral;

    private int count;

    private String species;

    @QueryProjection
    public SpeciesNeutralDto(int year, AnimalData.Neutral neutral, int count, String species) {
        this.year = year;
        this.neutral = neutral;
        this.count = count;
        this.species = species;
    }

    public SpeciesNeutral toEntity(){
        return SpeciesNeutral.builder()
                .year(year)
                .neutral(neutral)
                .count(count)
                .species(species)
                .build();
    }

}
