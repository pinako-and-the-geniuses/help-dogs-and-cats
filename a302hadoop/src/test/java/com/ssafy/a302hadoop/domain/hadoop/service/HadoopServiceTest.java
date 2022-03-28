package com.ssafy.a302hadoop.domain.hadoop.service;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import com.ssafy.a302hadoop.domain.hadoop.repository.AnimalDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
class HadoopServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private AnimalDataRepository animalDataRepository;


    private AnimalData animalDataTest;


    @BeforeEach
    void setUp() {
        animalDataRepository.deleteAll();

//        em.flush();
//        em.clear();

        animalDataTest = AnimalData.builder()
                .animalSpecies("개")
                .animalBreed("믹스")
                .neutralYn(AnimalData.Neutral.YES)
                .processState(AnimalData.ProcessState.PROTECT)
                .animalAge(1)
                .happenDt(2020)
                .animalCount(12)
                .happenArea("제주특별자치도")
                .build();

    }

    @Test
    @DisplayName("유기동물 데이터 등록 - 성공")
    void insertSuccess() {

        AnimalData animalData = animalDataRepository.save(animalDataTest);
//        em.flush();
//        em.clear();

        /*
         *
         * 유기동물 데이터를 불러옴
         *
         */
        AnimalData findAnimalData = animalDataRepository.findById(animalData.getSeq())
                .orElse(null);

        /*
        *  데이터 검증
        */

        assertThat(findAnimalData).isNotNull();
        assertThat(findAnimalData.getAnimalSpecies()).isEqualTo(animalDataTest.getAnimalSpecies());
        assertThat(findAnimalData.getAnimalBreed()).isEqualTo(animalDataTest.getAnimalBreed());
        assertThat(findAnimalData.getNeutralYn()).isEqualTo(animalDataTest.getNeutralYn());
        assertThat(findAnimalData.getProcessState()).isEqualTo(animalDataTest.getProcessState());
        assertThat(findAnimalData.getAnimalAge()).isEqualTo(animalDataTest.getAnimalAge());
        assertThat(findAnimalData.getHappenDt()).isEqualTo(animalDataTest.getHappenDt());
        assertThat(findAnimalData.getAnimalCount()).isEqualTo(animalDataTest.getAnimalCount());
        assertThat(findAnimalData.getHappenArea()).isEqualTo(animalDataTest.getHappenArea());

    }
}