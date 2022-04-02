package com.ssafy.a302hadoop.domain.hadoop.controller.dto;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(name = "연도별 유기동물 데이터 응답 DTO", description = "정제된 유기동물 데이터를 반환합니다.")
@Getter
public class AnimalDataResponseDto {

    @Schema(name = "연도별 유기동물 품종 마릿수 데이터 응답 DTO", description = "연도별로, 발생하는 유기동물들의 품종 데이터를 반환합니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"breed", "count"})
    public static class AnnualBreedInfo {

        @Schema(name = "breed", title = "품종", description = "품종 입니다.", example = "믹스견", required = true)
        private String breed;

        @Schema(name = "count", title = "마릿수", description = "해당 품종의 마릿수입니다.", example = "39039")
        private int count;

//        @Schema(name = "species", title = "동물종류", description = "동물종류 식별자 입니다.", example = "고양이")
//        private String species;

        @Builder
        public AnnualBreedInfo(String breed, int count) {
            this.breed = breed;
            this.count = count;
//            this.species = species;
        }
    }

    @Schema(name = "연도별 유기동물 처리 현황 데이터 응답 DTO", description = "연도별로, 발생하는 유기동물들의 처리현황 데이터를 반환합니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"state", "count"})
    public static class AnnualStateInfo {

        @Schema(name = "state", title = "처리현황", description = "유기동물의 처리 현황 정보입니다.", example = "보호중")
        private AnimalData.ProcessState state;

        @Schema(name = "count", title = "마릿수", description = "해당 품종의 마릿수입니다.", example = "39039")
        private int count;

//        @Schema(name = "species", title = "동물종류", description = "동물종류 식별자 입니다.", example = "고양이")
//        private String species;

        @Builder
        public AnnualStateInfo(AnimalData.ProcessState state, int count) {
            this.state = state;
            this.count = count;
//            this.species = species;
        }
    }

    @Schema(name = "연도별 유기동물 나이대별 처리현황 데이터 응답 DTO", description = "연도별로, 발생하는 유기동물들의 나이대별 처리 현황 데이터를 반환합니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"state", "count"})
    public static class AgeStateInfo {

//        @Schema(name = "age", title = "동물 나이", description = "유기동물의 나이 입니다.", example = "2")
//        private int age;

        @Schema(name = "state", title = "처리현황", description = "유기동물의 처리 현황 정보입니다.", example = "보호중")
        private AnimalData.ProcessState state;

        @Schema(name = "count", title = "마릿수", description = "해당 품종의 마릿수입니다.", example = "39039")
        private int count;

//        @Schema(name = "species", title = "동물종류", description = "동물종류 식별자 입니다.", example = "고양이")
//        private String species;

        @Builder
        public AgeStateInfo(AnimalData.ProcessState state, int count) {
//            this.age = age;
            this.state = state;
            this.count = count;
//            this.species = species;
        }

        public void changeCount(int newCount){
            this.count = newCount;
        }


    }

    @Schema(name = "연도별 유기동물 별 중성화 여부 데이터 응답 DTO", description = "연도별로, 발생하는 유기동물들의 중성화 여부 데이터를 반환합니다.")
    @Getter
    @NoArgsConstructor
    @ToString(of = {"neutral", "count"})
    public static class SpeciesNeutralInfo {

        @Schema(name = "neutral", title = "중성화 여부", description = "유기동물의 중성화 여부 정보입니다.", example = "ADOPTED")
        private AnimalData.Neutral neutral;

        @Schema(name = "count", title = "마릿수", description = "해당 품종의 마릿수입니다.", example = "39039")
        private int count;

//        @Schema(name = "species", title = "동물종류", description = "동물종류 식별자 입니다.", example = "고양이")
//        private String species;

        @Builder
        public SpeciesNeutralInfo(AnimalData.Neutral neutral, int count) {
            this.neutral = neutral;
            this.count = count;
//            this.species = species;
        }
    }
}
