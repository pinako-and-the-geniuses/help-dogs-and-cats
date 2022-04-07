package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString(of = {"processState", "noticeNo", "happenDate", "organizationName", "happenPlace", "popfileImagePath", "breedOfAnimal", "age", "weight", "sex", "neuterYn", "color", "specialMark", "shelterName", "shelterTel"})
public class animalDto {

    private final String processState;

    private final String noticeNo;

    private final LocalDate happenDate;

    private final String organizationName;

    private final String happenPlace;

    private final String popfileImagePath;

    private final String breedOfAnimal;

    private final String age;

    private final String weight;

    private final String sex;

    private final String neuterYn;

    private final String color;

    private final String specialMark;

    private final String shelterName;

    private final String shleterAddress;

    private final String shelterTel;

    @Builder
    public animalDto(String processState, String noticeNo, LocalDate happenDate, String organizationName, String happenPlace, String popfileImagePath, String breedOfAnimal, String age, String weight, String sex, String neuterYn, String color, String specialMark, String shelterName, String shleterAddress, String shelterTel) {
        this.processState = processState;
        this.noticeNo = noticeNo;
        this.happenDate = happenDate;
        this.organizationName = organizationName;
        this.happenPlace = happenPlace;
        this.popfileImagePath = popfileImagePath;
        this.breedOfAnimal = breedOfAnimal;
        this.age = age;
        this.weight = weight;
        this.sex = sex;
        this.neuterYn = neuterYn;
        this.color = color;
        this.specialMark = specialMark;
        this.shelterName = shelterName;
        this.shleterAddress = shleterAddress;
        this.shelterTel = shelterTel;
    }
}
