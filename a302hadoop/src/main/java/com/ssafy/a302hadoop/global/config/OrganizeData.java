package com.ssafy.a302hadoop.global.config;

import com.ssafy.a302hadoop.domain.hadoop.repository.*;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AgeStateDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AnnualBreedDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AnnualStateDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.SpeciesNeutralDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class OrganizeData {
    private final AnimalDataRepository animalDataRepository;

    private final AgeStateRepository ageStateRepository;
    private final AnnualBreedRepository annualBreedRepository;
    private final AnnualStateRepository annualStateRepository;
    private final SpeciesNeutralRepository speciesNeutralRepository;

    public void organizeData(int year) {

        List<AnnualBreedDto> annualBreedDtoList = animalDataRepository.setAnnualBreedList(year);
        List<AnnualStateDto> annualStateDtoList = animalDataRepository.setAnnualStateList(year);
        List<AgeStateDto> ageStateDtoList = animalDataRepository.setAgeStateList(year);
        List<SpeciesNeutralDto> speciesNeutralDtoList = animalDataRepository.setSpeciesNeutral(year);

        annualBreedRepository.deleteAll();
        annualBreedDtoList.forEach(a -> {
            if (a != null)
                annualBreedRepository.save(a.toEntity());
        });
        annualStateRepository.deleteAll();
        annualStateDtoList.forEach(a -> {
            if (a != null)
                annualStateRepository.save(a.toEntity());
        });
        ageStateRepository.deleteAll();
        ageStateDtoList.forEach(a -> {
            if (a != null) {
                ageStateRepository.save(a.toEntity());
            }
        });
        speciesNeutralRepository.deleteAll();
        speciesNeutralDtoList.forEach(a -> {
            if (a != null) {
                speciesNeutralRepository.save(a.toEntity());
            }
        });

    }
}
