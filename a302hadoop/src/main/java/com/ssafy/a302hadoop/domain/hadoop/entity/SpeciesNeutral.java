package com.ssafy.a302hadoop.domain.hadoop.entity;


import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_species_neutral"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "year", "neutral", "count", "species"})
public class SpeciesNeutral {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "data_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalData.Neutral neutral;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private String species;

    @Builder
    public SpeciesNeutral(int year, AnimalData.Neutral neutral, int count, String species) {
        this.year = year;
        this.neutral = neutral;
        this.count = count;
        this.species = species;
    }

    public AnimalDataResponseDto.SpeciesNeutralInfo toSpeciesNeutralInfo(){
        return AnimalDataResponseDto.SpeciesNeutralInfo.builder()
                .neutral(neutral)
                .count(count)
                .build();
    }
}
