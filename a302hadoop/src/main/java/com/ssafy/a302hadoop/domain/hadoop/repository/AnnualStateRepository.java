package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnnualState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualStateRepository extends JpaRepository<AnnualState, Long> {
    List<AnnualState> findAllByYearAndSpecies(int year, String species);
}


