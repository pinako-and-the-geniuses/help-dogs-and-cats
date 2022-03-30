package com.ssafy.a302hadoop.domain.hadoop.service;


import com.ssafy.a302hadoop.domain.hadoop.controller.dto.AnimalDataResponseDto;
import com.ssafy.a302hadoop.domain.hadoop.entity.AgeState;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualBreed;
import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualState;
import com.ssafy.a302hadoop.domain.hadoop.entity.SpeciesNeutral;
import com.ssafy.a302hadoop.domain.hadoop.repository.*;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.DataFromHadoopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HadoopServiceImpl implements HadoopService {

    private final AnimalDataRepository animalDataRepository;

    private final AnnualBreedRepository annualBreedRepository;

    private final AnnualStateRepository annualStateRepository;

    private final AgeStateRepository ageStateRepository;

    private final SpeciesNeutralRepository speciesNeutralRepository;



    @Override
    public void insertAnimalData(List<DataFromHadoopDto> datasFromHadoopDto, int year, String name) {
        int startYear = 0;

        if (name.equals("recentdata")) {
            startYear = year - 1;
        } else if (name.equals("alldata")) {
            startYear = 2017;
        }

        for (int i = startYear; i <= year; i++) {
            animalDataRepository.deleteAllByHappenDt(year);
        }

        for (DataFromHadoopDto data : datasFromHadoopDto){
            animalDataRepository.save(data.toAnimalDataEntity());
        }
    }

    @Override
    public Map<Integer, Map<String, Object>> getAnnualBreedData(LocalDate curDate) {
        Map<Integer, Map<String, Object>> result = new TreeMap<>();

        for (int i = 2017; i < curDate.getYear(); i++) {
            Map<String, Object> annualBreedInfoMap = new HashMap<>();

            List<AnimalDataResponseDto.AnnualBreedInfo> annualBreedInfos1 = new ArrayList<>();
            List<AnnualBreed> annualBreeds1 = annualBreedRepository.findAllByYearAndSpecies(i, "개");
            annualBreeds1.forEach(annualBreed -> annualBreedInfos1.add(annualBreed.toAnnualBreedInfo()));

            int totalDog = animalDataRepository.getAnimalCountInfo(i,"개");

            annualBreedInfos1.add(new AnimalDataResponseDto.AnnualBreedInfo("기타",totalDog - annualBreedRepository.getTotalFiveBreeds(i,"개") ));
            annualBreedInfoMap.put("개", annualBreedInfos1);
            annualBreedInfoMap.put("개Total", totalDog);

            List<AnimalDataResponseDto.AnnualBreedInfo> annualBreedInfos2 = new ArrayList<>();
            List<AnnualBreed> annualBreeds2 = annualBreedRepository.findAllByYearAndSpecies(i, "고양이");
            annualBreeds2.forEach(annualBreed -> annualBreedInfos2.add(annualBreed.toAnnualBreedInfo()));

            int totalCat = animalDataRepository.getAnimalCountInfo(i,"고양이");

            annualBreedInfos2.add(new AnimalDataResponseDto.AnnualBreedInfo("기타",totalCat - annualBreedRepository.getTotalFiveBreeds(i,"고양이") ));
            annualBreedInfoMap.put("고양이", annualBreedInfos2);
            annualBreedInfoMap.put("고양이Total", totalCat);

            result.put(i, annualBreedInfoMap);
        }

        return result;
    }

    @Override
    public Map<Integer, Map<String, List<AnimalDataResponseDto.AnnualStateInfo>>> getAnnualStateData(LocalDate curDate) {
        Map<Integer, Map<String, List<AnimalDataResponseDto.AnnualStateInfo>>> result = new TreeMap<>();
        for (int i = 2017; i < curDate.getYear(); i++) {
            Map<String, List<AnimalDataResponseDto.AnnualStateInfo>> annualStateInfoMap = new HashMap<>();

            List<AnimalDataResponseDto.AnnualStateInfo> annualStateInfos1 = new ArrayList<>();
            List<AnnualState> annualStates1 = annualStateRepository.findAllByYearAndSpecies(i, "개");
            annualStates1.forEach(annualState -> annualStateInfos1.add(annualState.toAnnualStateInfo()));

            List<AnimalDataResponseDto.AnnualStateInfo> annualStateInfos2 = new ArrayList<>();
            List<AnnualState> annualStates2 = annualStateRepository.findAllByYearAndSpecies(i, "고양이");
            annualStates2.forEach(annualState -> annualStateInfos2.add(annualState.toAnnualStateInfo()));

            annualStateInfoMap.put("개", annualStateInfos1);
            annualStateInfoMap.put("고양이", annualStateInfos2);

            result.put(i, annualStateInfoMap);

        }
        return result;
    }

    @Override
    public Map<Integer, Map<String, Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>>>> getAgeStateData(LocalDate curDate) {

        Map<Integer, Map<String, Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>>>> result = new HashMap<>();

        for (int i = 2017; i < curDate.getYear(); i++) {
            Map<String, Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>>> species = new HashMap<>();
            Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>> ageStateInfoMapDog = new HashMap<>();
            Map<Integer, List<AnimalDataResponseDto.AgeStateInfo>> ageStateInfoMapCat = new HashMap<>();

            for (int j = 0; j <= 13; j++) {
                //나이랑 정보
                List<AnimalDataResponseDto.AgeStateInfo> ageStateInfos1 = new ArrayList<>();
                List<AgeState> ageStates1 = ageStateRepository.findAllByAgeAndSpeciesAndYear(j, "개", i);
                if (ageStates1 != null) {
                    ageStates1.forEach(ageState -> ageStateInfos1.add(ageState.toAgeStateInfo()));
                }

                List<AnimalDataResponseDto.AgeStateInfo> ageStateInfos2 = new ArrayList<>();
                List<AgeState> ageStates2 = ageStateRepository.findAllByAgeAndSpeciesAndYear(j, "고양이", i);
                if (ageStates2 != null) {
                    ageStates2.forEach(ageState -> ageStateInfos2.add(ageState.toAgeStateInfo()));
                }

                ageStateInfoMapDog.put(j, ageStateInfos1);
                ageStateInfoMapCat.put(j, ageStateInfos2);
            }
            species.put("개", ageStateInfoMapDog);
            species.put("고양이", ageStateInfoMapCat);

            result.put(i, species);

        }

        return result;
    }

    @Override
    public Map<Integer, Map<String, Object > > getSpeciesNeutralData(LocalDate curDate) {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        for (int i = 2017; i < curDate.getYear(); i++) {
            Map<String, Object> speciesNeutralInfoMap = new HashMap<>();

            List<AnimalDataResponseDto.SpeciesNeutralInfo> speciesNeutralInfos1 = new ArrayList<>();
            List<SpeciesNeutral> speciesNeutrals1 = speciesNeutralRepository.findAllByYearAndSpecies(i, "개");
            speciesNeutrals1.forEach(speciesNeutral -> speciesNeutralInfos1.add(speciesNeutral.toSpeciesNeutralInfo()));

            List<AnimalDataResponseDto.SpeciesNeutralInfo> speciesNeutralInfos2 = new ArrayList<>();
            List<SpeciesNeutral> speciesNeutrals2 = speciesNeutralRepository.findAllByYearAndSpecies(i, "고양이");
            speciesNeutrals2.forEach(speciesNeutral -> speciesNeutralInfos2.add(speciesNeutral.toSpeciesNeutralInfo()));

            speciesNeutralInfoMap.put("개", speciesNeutralInfos1);
            speciesNeutralInfoMap.put("개Total", animalDataRepository.getAnimalCountInfo(i,"개"));
            speciesNeutralInfoMap.put("고양이", speciesNeutralInfos2);
            speciesNeutralInfoMap.put("고양이Total", animalDataRepository.getAnimalCountInfo(i,"고양이"));

            result.put(i, speciesNeutralInfoMap);

        }
        return result;

    }
}
