package com.ssafy.a302hadoop.domain.hadoop.entity;


import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_animal_data"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "animalSpecies", "animalBreed", "neutralYn", "processState", "animalAge", "happenDt", "animalCount", "happenArea"})
public class AnimalData {
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
    private String happenArea;

    @Builder
    public AnimalData(String animalSpecies, String animalBreed, Neutral neutralYn, ProcessState processState, int animalAge, int happenDt, int animalCount, String happenArea) {
        this.animalSpecies = animalSpecies;
        this.animalBreed = animalBreed;
        this.neutralYn = neutralYn;
        this.processState = processState;
        this.animalAge = animalAge;
        this.happenDt = happenDt;
        this.animalCount = animalCount;
        this.happenArea = happenArea;
    }

    public enum Neutral {
        YES("중성화 됨"),
        NO("중성화 안됨"),
        UNF("확인 불가");

        private final String description;

        Neutral(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }


    public enum ProcessState {

        ADOPTED("입양"),
        NATURALDEATH("자연사"),
        EUTHANASIA("안락사"),
        PROTECT("보호중"),
        EMISSION("방사"),
        DONATED("기증"),
        RETURNED("반환");
        




        private final String description;

        ProcessState(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


    }
}