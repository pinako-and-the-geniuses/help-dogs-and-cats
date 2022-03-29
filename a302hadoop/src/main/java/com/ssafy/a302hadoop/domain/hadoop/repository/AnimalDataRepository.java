package com.ssafy.a302hadoop.domain.hadoop.repository;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalDataRepository extends JpaRepository<AnimalData, Long> ,AnimalDataRepositoryCustom{

    void deleteAllByHappenDt(int year);

}


