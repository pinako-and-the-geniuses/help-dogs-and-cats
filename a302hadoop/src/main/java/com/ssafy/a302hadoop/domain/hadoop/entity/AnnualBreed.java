package com.ssafy.a302hadoop.domain.hadoop.entity;


import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_annual_breed_list"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "breed", "species", "year", "count"})
public class AnnualBreed {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "data_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int count;

    @Builder
    public AnnualBreed(String breed, String species, int year, int count) {
        this.breed = breed;
        this.species = species;
        this.year = year;
        this.count = count;
    }

    public AnimalDataResponseDto.AnnualBreedInfo toAnnualBreedInfo(){
        return AnimalDataResponseDto.AnnualBreedInfo.builder()
                .breed(breed)
                .count(count)
                .build();
    }

}
