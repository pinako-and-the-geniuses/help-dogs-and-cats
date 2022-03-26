package com.ssafy.a302hadoop.global.config;

import com.ssafy.a302hadoop.domain.hadoop.entity.AnimalData;
import com.ssafy.a302hadoop.domain.hadoop.service.HadoopService;
import com.ssafy.a302hadoop.domain.hadoop.service.dto.DataFromHadoopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class SaveAnimalDatas {

    private final HadoopService hadoopService;

//    public SaveAnimalDatas(HadoopService hadoopService) {
//        this.hadoopService = hadoopService;
//    }
//
//    public SaveAnimalDatas() {
//    }

    public void saveData(int year) throws IOException {

        for (int i = 0; i < 4; i++) {
            File file = new File("C:\\dev\\skeleton-hadoop-maven\\outputdata\\part-r-0000" + i);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);

            String line = "";

            List<DataFromHadoopDto> animalDataList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                //만약에 아무것도 없는 스트링이라면 continue;
                if (line.equals("")) continue;

                String[] data = line.split("\t");

                //tab으로 나누면 길이 2개짜리 배열이 나와야함
                if (data.length != 2) continue;

                //마지막 배열이 숫자여야함
                if (!isInteger(data[1])) continue;

                String[] animalInfo = data[0].split("-");

                //첫번째 배열이 "-"으로 split하면 총 6개로 나눠져야함
                if (animalInfo.length != 7) continue;

                String animalSpecies = animalInfo[0];

                String animalBreed = (animalInfo[1].equals("") ? "믹스" : animalInfo[1]);

                AnimalData.Neutral neutral = (animalInfo[2].equals("Y") ? AnimalData.Neutral.YES :
                        (animalInfo[2].equals("N") ? AnimalData.Neutral.NO : AnimalData.Neutral.UNF));

                AnimalData.ProcessState processState = null;

                switch (animalInfo[3]) {
                    case "자연사":
                        processState = AnimalData.ProcessState.NATURALDEATH;
                        break;
                    case "안락사":
                        processState = AnimalData.ProcessState.EUTHANASIA;
                        break;
                    case "보호중":
                        processState = AnimalData.ProcessState.PROTECT;
                        break;
                    case "입양":
                        processState = AnimalData.ProcessState.ADOPTED;
                        break;
                    case "반환":
                        processState = AnimalData.ProcessState.RETURNED;
                        break;
                    case "기증":
                        processState = AnimalData.ProcessState.DONATED;
                        break;
                    default:
                        processState = AnimalData.ProcessState.EMISSION;
                        break;
                }

                int animalAge = Integer.parseInt(animalInfo[4]);

                int happenDt = Integer.parseInt(animalInfo[5]);

                String happenArea = animalInfo[6];

                int animalCount = Integer.parseInt(data[1]);

//                System.out.println("====================================");
//                System.out.println("animalSpecies = " + animalSpecies.trim());
//                System.out.println("animalBreed = " + animalBreed.trim());
//                System.out.println("neutral = " + neutral);
//                System.out.println("processState = " + processState);
//                System.out.println("animalAge = " + animalAge);
//                System.out.println("happenDt = " + happenDt);
//                System.out.println("happenArea = " + happenArea.trim());
//                System.out.println("animalCount = " + animalCount);
//                System.out.println("====================================");

                animalDataList.add(DataFromHadoopDto.builder()
                        .animalSpecies(animalSpecies)
                        .animalBreed(animalBreed)
                        .neutralYn(neutral)
                        .processState(processState)
                        .animalAge(animalAge)
                        .happenDt(happenDt)
                        .happenArea(happenArea)
                        .animalCount(animalCount)
                        .build());
            }

            hadoopService.insertAnimalData(animalDataList, year);
        }

    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
