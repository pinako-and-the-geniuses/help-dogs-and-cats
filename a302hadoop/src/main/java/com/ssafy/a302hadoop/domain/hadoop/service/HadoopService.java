package com.ssafy.a302hadoop.domain.hadoop.service;

import com.ssafy.a302hadoop.domain.hadoop.service.dto.DataFromHadoopDto;

import java.util.List;

public interface HadoopService {

    void insertAnimalData(List<DataFromHadoopDto> datasFromHadoopDto, int year);
}
