package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.SpeciesNeutral;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AgeStateDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AnnualBreedDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.AnnualStateDto;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.SpeciesNeutralDto;

import java.util.List;

public interface AnimalDataRepositoryCustom{

    List<AnnualBreedDto> setAnnualBreedList(int curYear);

    List<AnnualStateDto> setAnnualStateList(int curYear);

    List<AgeStateDto> setAgeStateList(int curYear);

    List<SpeciesNeutralDto> setSpeciesNeutral(int curYear);

}


