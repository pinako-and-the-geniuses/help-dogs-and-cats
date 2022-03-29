package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualBreed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualBreedRepository extends JpaRepository<AnnualBreed, Long> , AnnualBreedRepositoryCustom{
    List<AnnualBreed> findAllByYearAndSpecies(int year, String species);


}


