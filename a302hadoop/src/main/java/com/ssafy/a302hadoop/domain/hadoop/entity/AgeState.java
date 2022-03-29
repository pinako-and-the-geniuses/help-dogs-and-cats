package com.ssafy.a302hadoop.domain.hadoop.entity;

import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(
        name = "tb_age_state_list"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "year", "age", "species", "state", "count"})
public class AgeState {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "data_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalData.ProcessState state;

    @Column(nullable = false)
    private int count;


    @Builder
    public AgeState(int year, int age, String species, AnimalData.ProcessState state, int count) {
        this.year = year;
        this.age = age;
        this.species = species;
        this.state = state;
        this.count = count;
    }

    public AnimalDataResponseDto.AgeStateInfo toAgeStateInfo(){
        return AnimalDataResponseDto.AgeStateInfo.builder()
                .state(state)
                .count(count)
                .build();
    }
}
