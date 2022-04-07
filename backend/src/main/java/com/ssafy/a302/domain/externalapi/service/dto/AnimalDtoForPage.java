package com.ssafy.a302.domain.externalapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString(of = {"processState", "noticeNo", "happenDate", "organizationName", "happenPlace", "popfileImagePath"})
public class AnimalDtoForPage {

    private final String processState;

    private final String noticeNo;

    private final LocalDate happenDate;

    private final String organizationName;

    private final String happenPlace;

    private final String popfileImagePath;

    @Builder
    public AnimalDtoForPage(String processState, String noticeNo, LocalDate happenDate, String organizationName, String happenPlace, String popfileImagePath) {
        this.processState = processState;
        this.noticeNo = noticeNo;
        this.happenDate = happenDate;
        this.organizationName = organizationName;
        this.happenPlace = happenPlace;
        this.popfileImagePath = popfileImagePath;
    }
}
