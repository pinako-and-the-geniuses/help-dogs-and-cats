package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"shelterName", "organizationName", "divisionName", "saveTargetAnimal", "address", "jibunAddress", "closeDay", "vetPersonCount", "specsPersonCount", "tel", "weekOperationStartTime", "weekOperationEndTime", "lat", "lng"})
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

    private final String weekOperationStartTime;

    private final String weekOperationEndTime;

    private final String lat;

    private final String lng;

    @Builder
    public ShelterDto(String shelterName, String organizationName, String divisionName, String saveTargetAnimal, String address, String jibunAddress, String closeDay, Integer vetPersonCount, Integer specsPersonCount, String tel, String weekOperationStartTime, String weekOperationEndTime, String lat, String lng) {
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
        this.weekOperationStartTime = weekOperationStartTime;
        this.weekOperationEndTime = weekOperationEndTime;
        this.lat = lat;
        this.lng = lng;
    }
}
