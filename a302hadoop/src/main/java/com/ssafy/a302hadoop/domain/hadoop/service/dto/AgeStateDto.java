package com.ssafy.a302hadoop.domain.hadoop.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.a302hadoop.domain.hadoop.entity.AgeState;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AgeStateDto {

    private int year;

    private int age;

    private String species;

    private AnimalData.ProcessState state;

    private int count;

    @QueryProjection
    public AgeStateDto(int year, int age, AnimalData.ProcessState state, int count,String species) {
        this.year = year;
        this.age = age;
        this.species = species;
        this.state = state;
        this.count = count;
    }

    public AgeState toEntity(){
        return AgeState.builder()
                .year(year)
                .age(age)
                .species(species)
                .state(state)
                .count(count)
                .build();
    }
}
