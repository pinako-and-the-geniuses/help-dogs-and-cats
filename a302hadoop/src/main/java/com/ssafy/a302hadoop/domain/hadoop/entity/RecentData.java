package com.ssafy.a302hadoop.domain.hadoop.entity;


import com.ssafy.a302hadoop.domain.hadoop.entity.enums.Neutral;
import com.ssafy.a302hadoop.domain.hadoop.entity.enums.ProcessState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_recent_data"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@ToString(of = {"seq", "species", "breed", "neutral", "processState", "age", "year", "count", "area"})
public class RecentData {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "data_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String animalSpecies;

    @Column(nullable = false)
    private String animalBreed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Neutral neutralYn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessState processState;

    @Column(nullable = false)
    private int animalAge;

    @Column(nullable = false)
    private int happenDt;

    @Column(nullable = false)
    private int animalCount;

    @Column(nullable = false)
    private int happenArea;
    
    @Builder
    public RecentData(String animalSpecies, String animalBreed, Neutral neutralYn, ProcessState processState, int animalAge, int happenDt, int animalCount, int happenArea) {
        this.animalSpecies = animalSpecies;
        this.animalBreed = animalBreed;
        this.neutralYn = neutralYn;
        this.processState = processState;
        this.animalAge = animalAge;
        this.happenDt = happenDt;
        this.animalCount = animalCount;
        this.happenArea = happenArea;
    }
}