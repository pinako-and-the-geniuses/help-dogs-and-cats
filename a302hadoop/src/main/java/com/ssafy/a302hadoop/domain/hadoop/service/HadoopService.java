package com.ssafy.a302hadoop.domain.hadoop.service;

import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.DataFromHadoopDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HadoopService {

    void insertAnimalData(List<DataFromHadoopDto> datasFromHadoopDto, int year, String name);

    Map<Integer, Map<String, Object>> getAnnualBreedData(LocalDate curDate);

    Map<Integer, Map<String, List<AnimalDataResponseDto.AnnualStateInfo>>> getAnnualStateData(LocalDate curDate);

    Map<Integer, Map<String, Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>>>> getAgeStateData(LocalDate curDate);

    Map<Integer, Map<String,Object>> getSpeciesNeutralData(LocalDate curDate);

}
