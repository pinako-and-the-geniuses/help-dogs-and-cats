package com.ssafy.a302hadoop.global.scheduler;

import com.ssafy.a302hadoop.global.config.GetLastYearData;
import com.ssafy.a302hadoop.global.config.GetThisYearData;
import com.ssafy.a302hadoop.global.config.SaveAnimalDatas;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class OpenApiDataSave {

    @Autowired
    SaveAnimalDatas saveAnimalDatas;

    @Value("${openapi.serviceKey}")
    private String serviceKey;

    //30분 마다 최근 2년치 데이터 받아와서 db에 저장해줘야함
    @Scheduled(cron = "0 10,40 * * * *")
    public void getRecentAnimalData() throws IOException, ParseException {

        LocalDate curDate = LocalDate.now();

        GetLastYearData glyd = new GetLastYearData();

        glyd.getLastYearData(curDate.getYear() - 1, "recent", false, serviceKey);

        GetThisYearData gtyd = new GetThisYearData();

        gtyd.getThisYearData(curDate, serviceKey);

        //sh코드 실행
        // https://codechacha.com/ko/java-run-shell-script/ 참고하자

        //db저장


        saveAnimalDatas.saveData(LocalDate.now().getYear());



    }

//    //매년 초가 되면 2년전의 데이터를 영구 저장해줌
//    @Scheduled(cron = "0 10 0 1 1 ?")
//    public void saveLastTwoYearData() throws IOException, ParseException {
//
//        LocalDate curDate = LocalDate.now();
//
//        GetLastYearData glyd = new GetLastYearData();
//
//        glyd.getLastYearData(curDate.getYear() - 2, "lastTwoYearData", false, serviceKey);
//
//        //sh코드 실행
//
//        //db저장
//    }


}
