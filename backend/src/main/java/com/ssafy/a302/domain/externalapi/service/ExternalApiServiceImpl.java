package com.ssafy.a302.domain.externalapi.service;

import com.ssafy.a302.domain.externalapi.service.dto.*;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
                    .weekOperationStartTime((String) obj.get("weekOprStime"))
                    .weekOperationEndTime((String) obj.get("weekOprEtime"))
                    .lat(String.valueOf(obj.get("lat")))
                    .lng(String.valueOf(obj.get("lng")))
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
                            .weekOperationStartTime((String) obj.get("weekOprStime"))
                            .weekOperationEndTime((String) obj.get("weekOprEtime"))
                            .lat(String.valueOf(obj.get("lat")))
                            .lng(String.valueOf(obj.get("lng")))
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

    @Override
    public ShelterDto getShelterDto(String shelterName) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(shelterUrl.concat("/shelterInfo"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + shelterKey);
        urlBuilder.append("&" + URLEncoder.encode("care_nm", "UTF-8") + "=" + URLEncoder.encode(shelterName, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());
        JSONArray jsonArray = getJsonArray(result);
        JSONObject obj = (JSONObject) jsonArray.get(0);
        return ShelterDto.builder()
                .shelterName((String) obj.get("careNm"))
                .organizationName((String) obj.get("orgNm"))
                .saveTargetAnimal((String) obj.get("saveTrgtAnimal"))
                .address((String) obj.get("careAddr"))
                .jibunAddress((String) obj.get("jibunAddr"))
                .closeDay((String) obj.get("closeDay"))
                .vetPersonCount(obj.get("vetPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("vetPersonCnt"))))
                .specsPersonCount(obj.get("specsPersonCnt") == null ? null : Integer.parseInt(String.valueOf(obj.get("specsPersonCnt"))))
                .tel((String) obj.get("careTel"))
                .weekOperationStartTime((String) obj.get("weekOprStime"))
                .weekOperationEndTime((String) obj.get("weekOprEtime"))
                .lat(String.valueOf(obj.get("lat")))
                .lng(String.valueOf(obj.get("lng")))
                .build();
    }

    @Override
    public List<ShelterMiniDto> getShelterMiniDtos(String sidoCode, String sigunguCode) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/shelter"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + sidoCode);
        urlBuilder.append("&" + URLEncoder.encode("org_cd", "UTF-8") + "=" + sigunguCode);
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        String result = httpUtil.getResult(urlBuilder.toString());
        JSONArray jsonArray = getJsonArray(result);

        List<ShelterMiniDto> shelterMiniDtos = new ArrayList<>();
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                String shelterCode = (String) obj.get("careRegNo");
                String shelterName = (String) obj.get("careNm");
                shelterMiniDtos.add(ShelterMiniDto.builder()
                        .code(shelterCode)
                        .name(shelterName)
                        .build());
            }
        }

        return shelterMiniDtos;
    }

    @Override
    public AnimalPageDto getAnimalPageDto(Pageable pageable, String sidoCode, String sigunguCode, String shelterCode, String upkind, String state) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(animalUrl.concat("/abandonmentPublic"));
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + animalKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + pageable.getPageNumber());
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + pageable.getPageSize());
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        if (StringUtils.hasText(sidoCode)) {
            urlBuilder.append("&" + URLEncoder.encode("upr_cd", "UTF-8") + "=" + sidoCode);
        }

        if (StringUtils.hasText(sigunguCode)) {
            urlBuilder.append("&" + URLEncoder.encode("org_cd", "UTF-8") + "=" + sigunguCode);
        }

        if (StringUtils.hasText(shelterCode)) {
            urlBuilder.append("&" + URLEncoder.encode("care_reg_no", "UTF-8") + "=" + shelterCode);
        }

        if (StringUtils.hasText(upkind)) {
            urlBuilder.append("&" + URLEncoder.encode("upkind", "UTF-8") + "=" + upkind);
        }

        if (StringUtils.hasText(state)) {
            urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + state);
        }

        String result = httpUtil.getResult(urlBuilder.toString());
        JSONArray jsonArray = getJsonArray(result);

        List<AnimalDtoForPage> animalDtoForPages = new ArrayList<>();

        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;

                StringBuilder dateBuilder = new StringBuilder((String) obj.get("happenDt"));
                dateBuilder.insert(6, "-");
                dateBuilder.insert(4, "-");

                animalDtoForPages.add(AnimalDtoForPage.builder()
                        .processState((String) obj.get("processState"))
                        .noticeNo((String) obj.get("noticeNo"))
                        .happenDate(LocalDate.parse(dateBuilder.toString()))
                        .organizationName((String) obj.get("orgNm"))
                        .happenPlace((String) obj.get("happenPlace"))
                        .popfileImagePath((String) obj.get("popfile"))
                        .build());
            }
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        int totalCount = Integer.parseInt(String.valueOf(((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("totalCount")));
        int totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        return AnimalPageDto.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .currentPageNumber(pageable.getPageNumber())
                .animalDtoForPages(animalDtoForPages)
                .build();
    }

    private static JSONArray getJsonArray(String result) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        return (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");
    }
}
