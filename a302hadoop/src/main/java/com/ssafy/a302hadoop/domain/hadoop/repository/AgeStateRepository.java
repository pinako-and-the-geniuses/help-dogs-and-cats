package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.AgeState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgeStateRepository extends JpaRepository<AgeState, Long> {

    List<AgeState> findAllByAgeAndSpeciesAndYear(int age, String species, int year);
}


