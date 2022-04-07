package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"shelterName", "organizationName", "divisionName", "saveTargetAnimal", "address", "jibunAddress", "closeDay", "vetPersonCount", "specsPersonCount", "tel"})
public class ShelterDto {

    private final String shelterName;

    private final String organizationName;

    private final String divisionName;

    private final String saveTargetAnimal;

    private final String address;

    private final String jibunAddress;

    private final String closeDay;

    private final Integer vetPersonCount;

    private final Integer specsPersonCount;

    private final String tel;

    private final String weekOperationStartTIme;

    private final String weekOperationEndTime;

    @Builder
    public ShelterDto(String shelterName, String organizationName, String divisionName, String saveTargetAnimal, String address, String jibunAddress, String closeDay, Integer vetPersonCount, Integer specsPersonCount, String tel, String weekOperationStartTIme, String weekOperationEndTime) {
        this.shelterName = shelterName;
        this.organizationName = organizationName;
        this.divisionName = divisionName;
        this.saveTargetAnimal = saveTargetAnimal;
        this.address = address;
        this.jibunAddress = jibunAddress;
        this.closeDay = closeDay;
        this.vetPersonCount = vetPersonCount;
        this.specsPersonCount = specsPersonCount;
        this.tel = tel;
        this.weekOperationStartTIme = weekOperationStartTIme;
        this.weekOperationEndTime = weekOperationEndTime;
    }
}
