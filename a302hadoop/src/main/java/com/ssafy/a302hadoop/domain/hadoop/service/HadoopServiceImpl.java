package com.ssafy.a302hadoop.domain.hadoop.service;


import com.ssafy.a302hadoop.domain.hadoop.repository.AnimalDataRepository;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.DataFromHadoopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HadoopServiceImpl implements HadoopService{

    private final AnimalDataRepository animalDataRepository;

    @Override
    public void insertAnimalData(List<DataFromHadoopDto> datasFromHadoopDto, int year, String name) {
        int startYear = 0;

        if(name.equals("recentdata")){
            startYear = year - 1;
        }else if(name.equals("alldata")){
            startYear = 2017;
        }

        for(int i = startYear ; i <= year ; i++){
            animalDataRepository.deleteAllByHappenDt(year);
        }

        for (DataFromHadoopDto data : datasFromHadoopDto){
            animalDataRepository.save(data.toAnimalDataEntity());
        }
    }
}
