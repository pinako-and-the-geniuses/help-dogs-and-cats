package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a302hadoop.domain.hadoop.entity.AgeState;
import com.ssafy.a302hadoop.domain.hadoop.entity.SpeciesNeutral;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.a302hadoop.domain.hadoop.entity.QAnimalData.animalData;

public class AnimalDataRepositoryImpl implements AnimalDataRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AnimalDataRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<AnnualBreedDto> setAnnualBreedList(int curYear) {

        List<AnnualBreedDto> annualBreeds = new ArrayList<>();

        for (int i = 2017; i < curYear; i++) {
            annualBreeds.addAll(queryFactory
                    .select(new QAnnualBreedDto(
                            animalData.happenDt,
                            animalData.animalBreed,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies

                    )).from(animalData)
                    .where(animalData.animalSpecies.eq("개")
                            .and(animalData.happenDt.eq(i)))
                    .groupBy(animalData.animalBreed)
                    .orderBy(animalData.animalCount.sum().desc())
                    .limit(5)
                    .fetch());

            annualBreeds.addAll(queryFactory
                    .select(new QAnnualBreedDto(
                            animalData.happenDt,
                            animalData.animalBreed,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies

                    )).from(animalData)
                    .where(animalData.animalSpecies.eq("고양이")
                            .and(animalData.happenDt.eq(i)))
                    .groupBy(animalData.animalBreed)
                    .orderBy(animalData.animalCount.sum().desc())
                    .limit(5)
                    .fetch());
        }
        return annualBreeds;
    }

    @Override
    public List<AnnualStateDto> setAnnualStateList(int curYear) {

        List<AnnualStateDto> annualStateDtoList = new ArrayList<>();

        for (int i = 2017; i < curYear; i++) {
            annualStateDtoList.addAll(queryFactory
                    .select(new QAnnualStateDto(
                            animalData.happenDt,
                            animalData.processState,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("개")))
                    .groupBy(animalData.happenDt, animalData.processState)
                    .fetch());
            
            annualStateDtoList.addAll(queryFactory
                    .select(new QAnnualStateDto(
                            animalData.happenDt,
                            animalData.processState,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("고양이")))
                    .groupBy(animalData.happenDt, animalData.processState)
                    .fetch());
        }
        return annualStateDtoList;
    }

    @Override
    public List<AgeStateDto> setAgeStateList(int curYear) {
        List<AgeStateDto> ageStateList = new ArrayList<>();

        for(int i = 2017; i < curYear; i++){
            ageStateList.addAll(queryFactory
                    .select(new QAgeStateDto(
                            animalData.happenDt,
                            animalData.animalAge,
                            animalData.processState,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("개")))
                    .groupBy(animalData.happenDt,animalData.animalAge,animalData.processState)
                    .fetch());
            ageStateList.addAll(queryFactory
                    .select(new QAgeStateDto(
                            animalData.happenDt,
                            animalData.animalAge,
                            animalData.processState,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("고양이")))
                    .groupBy(animalData.animalAge,animalData.processState)
                    .fetch());
        }
        return ageStateList;
    }

    @Override
    public List<SpeciesNeutralDto> setSpeciesNeutral(int curYear) {
        List<SpeciesNeutralDto> speciesNeutralDtoList = new ArrayList<>();

        for(int i = 2017; i < curYear; i++){
            speciesNeutralDtoList.addAll(queryFactory
                    .select(new QSpeciesNeutralDto(
                            animalData.happenDt,
                            animalData.neutralYn,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("개")))
                    .groupBy(animalData.happenDt,animalData.neutralYn)
                    .fetch());

            speciesNeutralDtoList.addAll(queryFactory
                    .select(new QSpeciesNeutralDto(
                            animalData.happenDt,
                            animalData.neutralYn,
                            animalData.animalCount.sum(),
                            animalData.animalSpecies
                    )).from(animalData)
                    .where(animalData.happenDt.eq(i).and(animalData.animalSpecies.eq("고양이")))
                    .groupBy(animalData.happenDt,animalData.neutralYn)
                    .fetch());
        }
        return speciesNeutralDtoList;
    }
}
