package com.ssafy.a302hadoop.domain.hadoop.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualBreed;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
public class AnnualBreedDto {

    private int year;

    private String breed;

    private int count;

    private String species;

    @QueryProjection
    public AnnualBreedDto(int year, String breed, int count, String species) {
        this.year = year;
        this.breed = breed;
        this.count = count;
        this.species = species;
    }
    
    public AnnualBreed toEntity() {
        return AnnualBreed.builder()
                .year(year)
                .breed(breed)
                .count(count)
                .species(species)
                .build();
    }
}
