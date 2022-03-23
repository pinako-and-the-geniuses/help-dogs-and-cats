package com.ssafy.hadoop.global.config;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class OpenApiDataSave {

    @Value("${openapi.serviceKey}")
    private String serviceKey;

    //30분 마다 최근 2년치 데이터 받아와서 db에 저장해줘야함
    @Async
    @Scheduled(cron = "0 0/30 * * * *")
    public void getRecentAnimalData() throws IOException, ParseException {

        LocalDate curDate = LocalDate.now();

        GetLastYearData glyd = new GetLastYearData();

        glyd.getLastYearData(curDate.getYear(),"recent",false);

        GetThisYearData gtyd = new GetThisYearData();

        gtyd.getThisYearData(curDate);

        //sh코드 실행

        //db저장

    }

    //매년 초가 되면 2년전의 데이터를 영구 저장해줌
    @Async
    @Scheduled(cron = "0 10 0 1 1 ?")
    public void saveLastTwoYearData() throws IOException, ParseException {

        LocalDate curDate = LocalDate.now();

        GetLastYearData glyd = new GetLastYearData();

        glyd.getLastYearData(curDate.getYear()-2,"lastTwoYearData", false);

        //sh코드 실행

        //db저장
    }


}
