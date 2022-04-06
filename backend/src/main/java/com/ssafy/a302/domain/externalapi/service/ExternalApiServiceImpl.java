package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.SidoDto;
import com.ssafy.a302.domain.externalapi.service.dto.SigunguDto;
import com.ssafy.a302.global.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExternalApiServiceImpl implements ExternalApiService {

    private final HttpUtil httpUtil;

    @Value("${key.animal}")
    private String animalKey;

    @Value("${url.animal}")
    private String animalUrl;

    @Override
    public List<SidoDto> getSidoDtos() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/sido"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=17");
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());

        List<SidoDto> sidos = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            String sidoCode = (String) obj.get("orgCd");
            String name = (String) obj.get("orgdownNm");
            sidos.add(SidoDto.builder()
                    .sidoCode(sidoCode)
                    .name(name)
                    .build());
        }

        return sidos;
    }

    @Override
    public List<SigunguDto> getSigunguDtos(String sidoCode) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/sigungu"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + sidoCode);
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());

        List<SigunguDto> sigunguDtos = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            String findSidoCode = (String) obj.get("uprCd");
            String sigunguCode = (String) obj.get("orgCd");
            String name = (String) obj.get("orgdownNm");
            sigunguDtos.add(SigunguDto.builder()
                    .sidoCode(findSidoCode)
                    .sigunguCode(sigunguCode)
                    .name(name)
                    .build());
        }

        return sigunguDtos;
    }
}
