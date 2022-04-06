package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.SidoDto;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ExternalApiService {

    List<SidoDto> getSido() throws IOException, ParseException;
}
