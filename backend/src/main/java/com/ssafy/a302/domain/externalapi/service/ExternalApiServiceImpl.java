package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.ShelterDto;
import com.ssafy.a302.domain.externalapi.service.dto.ShelterPageDto;
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
import org.springframework.data.domain.Pageable;
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

    @Value("${key.shelter}")
    private String shelterKey;

    @Value("${url.shelter}")
    private String shelterUrl;

    @Override
    public List<SidoDto> getSidoDtos() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/sido"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=17");
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());

        List<SidoDto> sidos = new ArrayList<>();
        JSONArray jsonArray = getJsonArray(result);
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
        JSONArray jsonArray = getJsonArray(result);
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

    @Override
    public ShelterPageDto getShelterPageDto(Pageable pageable) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(shelterUrl.concat("/shelterInfo"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + shelterKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + pageable.getPageNumber());
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + pageable.getPageSize());
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());

        List<ShelterDto> shelterDtos = new ArrayList<>();

        JSONArray jsonArray = getJsonArray(result);
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            shelterDtos.add(ShelterDto.builder()
                    .shelterName((String) obj.get("careNm"))
                    .organizationName((String) obj.get("orgNm"))
                    .saveTargetAnimal((String) obj.get("saveTrgtAnimal"))
                    .address((String) obj.get("careAddr"))
                    .jibunAddress((String) obj.get("jibunAddr"))
                    .closeDay((String) obj.get("closeDay"))
                    .vetPersonCount(obj.get("vetPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("vetPersonCnt"))))
                    .specsPersonCount(obj.get("specsPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("specsPersonCnt"))))
                    .tel((String) obj.get("careTel"))
                    .build());
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        int totalCount = Integer.parseInt(String.valueOf(((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("totalCount")));
        int totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        return ShelterPageDto.builder()
                .totalCount(totalCount)
                .currentPageNumber(pageable.getPageNumber())
                .totalPageNumber(totalPageNumber)
                .shelterDtos(shelterDtos)
                .build();
    }

    @Override
    public ShelterPageDto getShelterPageDto(Pageable pageable, String sidoCode, String sigunguCode) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/shelter"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + sidoCode);
        urlBuilder.append("&" + URLEncoder.encode("org_cd", "UTF-8") + "=" + sigunguCode);
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());
        JSONArray jsonArray = getJsonArray(result);

        List<ShelterDto> shelterDtos = new ArrayList<>();
        int totalCount = 0;
        if (jsonArray != null) {
            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();
            for (int i = (page - 1) * size, SIZE = Math.min(page * size, jsonArray.size()); i < SIZE; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String shelterNo = (String) jsonObject.get("careRegNo");

                urlBuilder = new StringBuilder(shelterUrl.concat("/shelterInfo"));
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + shelterKey);
                urlBuilder.append("&" + URLEncoder.encode("care_reg_no", "UTF-8") + "=" + shelterNo);
                urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

                result = httpUtil.getResult(urlBuilder.toString());
                JSONArray jsonArr = getJsonArray(result);
                if (jsonArr != null) {
                    ++totalCount;
                    JSONObject obj = (JSONObject) jsonArr.get(0);
                    shelterDtos.add(ShelterDto.builder()
                            .shelterName((String) obj.get("careNm"))
                            .organizationName((String) obj.get("orgNm"))
                            .saveTargetAnimal((String) obj.get("saveTrgtAnimal"))
                            .address((String) obj.get("careAddr"))
                            .jibunAddress((String) obj.get("jibunAddr"))
                            .closeDay((String) obj.get("closeDay"))
                            .vetPersonCount(obj.get("vetPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("vetPersonCnt"))))
                            .specsPersonCount(obj.get("specsPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("specsPersonCnt"))))
                            .tel((String) obj.get("careTel"))
                            .build());
                }
            }
        }

        int totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        return ShelterPageDto.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .currentPageNumber(pageable.getPageNumber())
                .shelterDtos(shelterDtos)
                .build();
    }

    private static JSONArray getJsonArray(String result) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        return (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");
    }
}
