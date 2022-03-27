package com.ssafy.a302hadoop.domain.hadoop.service.dto;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class DataFromHadoopDto {

    @Schema(name = "animalSpecies", title = "동물 종류", description = "동물의 종류입니다. 개 or 고양이")
    private final String animalSpecies;

    @Schema(name = "animalBreed", title = "품종", description = "품종입니다.")
    private final String animalBreed;

    @Schema(name = "neutralYn", title = "중성화 여부", description = "중성화 여부입니다.")
    private final AnimalData.Neutral neutralYn;

    @Schema(name = "processState", title = "현재 상태", description = "보호, 입양등의 동물의 현재 상태입니다.")
    private final AnimalData.ProcessState processState;

    @Schema(name = "animalAge", title = "동물 나이", description = "동물의 나이입니다.")
    private final int animalAge;

    @Schema(name = "happenDt", title = "발견 날짜", description = "유기동물이 발견된 날짜입니다.")
    private final int happenDt;

    @Schema(name = "animalCount", title = "마릿수", description = "발생한 유기동물의 수입니다.")
    private final int animalCount;

    @Schema(name = "happenArea", title = "발견 장소", description = "지역 정보 입니다.")
    private final String happenArea;

    @Builder
    public DataFromHadoopDto(String animalSpecies, String animalBreed, AnimalData.Neutral neutralYn, AnimalData.ProcessState processState, int animalAge, int happenDt, int animalCount, String happenArea) {
        this.animalSpecies = animalSpecies;
        this.animalBreed = animalBreed;
        this.neutralYn = neutralYn;
        this.processState = processState;
        this.animalAge = animalAge;
        this.happenDt = happenDt;
        this.animalCount = animalCount;
        this.happenArea = happenArea;
    }

    public AnimalData toAnimalDataEntity(){
        return AnimalData.builder()
                .animalSpecies(animalSpecies)
                .animalBreed(animalBreed)
                .neutralYn(neutralYn)
                .processState(processState)
                .animalAge(animalAge)
                .happenDt(happenDt)
                .animalCount(animalCount)
                .happenArea(happenArea)
                .build();
    }


}
