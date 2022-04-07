package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.*;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ExternalApiService {

    List<SidoDto> getSidoDtos() throws IOException, ParseException;

    List<SigunguDto> getSigunguDtos(String sidoCode) throws IOException, ParseException;

    ShelterPageDto getShelterPageDto(Pageable pageable) throws IOException, ParseException;

    ShelterPageDto getShelterPageDto(Pageable pageable, String sidoCode, String sigunguCode) throws IOException, ParseException;

    ShelterDto getShelterDto(String shelterName) throws IOException, ParseException;

    List<ShelterMiniDto> getShelterMiniDtos(String sidoCdoe, String sigunguCode) throws IOException, ParseException;

    AnimalPageDto getAnimalPageDto(Pageable pageable, String sidoCode, String sigunguCode, String shelterCode, String upkind, String state) throws IOException, ParseException;
}
