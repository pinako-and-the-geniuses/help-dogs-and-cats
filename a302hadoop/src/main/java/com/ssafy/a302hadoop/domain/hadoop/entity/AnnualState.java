package com.ssafy.a302hadoop.domain.hadoop.entity;

import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(
        name = "tb_annual_state_list"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "species", "state", "year", "count"})
public class AnnualState {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "data_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalData.ProcessState state;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private int count;

    @Builder
    public AnnualState(String species, AnimalData.ProcessState state, int year, int count) {
        this.species = species;
        this.state = state;
        this.year = year;
        this.count = count;
    }

    public AnimalDataResponseDto.AnnualStateInfo toAnnualStateInfo(){
        return AnimalDataResponseDto.AnnualStateInfo.builder()
                .state(state)
                .count(count)
                .build();
    }
}
