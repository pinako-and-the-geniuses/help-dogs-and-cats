package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.SidoDto;
import com.ssafy.a302.domain.externalapi.service.dto.SigunguDto;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ExternalApiService {

    List<SidoDto> getSidoDtos() throws IOException, ParseException;

    List<SigunguDto> getSigunguDtos(String sidoCode) throws IOException, ParseException;
}
