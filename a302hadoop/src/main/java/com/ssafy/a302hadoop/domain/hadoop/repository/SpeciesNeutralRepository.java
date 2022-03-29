package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.SpeciesNeutral;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesNeutralRepository extends JpaRepository<SpeciesNeutral, Long> {
    List<SpeciesNeutral> findAllByYearAndSpecies(int year, String species);
}


