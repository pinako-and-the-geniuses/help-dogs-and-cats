package com.ssafy.a302hadoop.global.scheduler;

import com.ssafy.a302hadoop.global.config.GetLastYearData;
import com.ssafy.a302hadoop.global.config.GetThisYearData;
import com.ssafy.a302hadoop.global.config.OrganizeData;
import com.ssafy.a302hadoop.global.config.SaveAnimalDatas;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class OpenApiDataSave {

    private final SaveAnimalDatas saveAnimalDatas;
    private final OrganizeData organizeData;

    @Value("${openapi.serviceKey}")
    private String serviceKey;

//    30분 마다 최근 2년치 데이터 받아와서 db에 저장해줘야함
    @Scheduled(cron = "0 30 18 * * *")
    public void getRecentAnimalData() throws IOException, ParseException {

        LocalDate curDate = LocalDate.now();

        GetLastYearData glyd = new GetLastYearData();

        glyd.getLastYearData(curDate.getYear() - 1, "recentdata", false, serviceKey);

        GetThisYearData gtyd = new GetThisYearData();

        gtyd.getThisYearData(curDate, serviceKey);

        //sh코드 실행
        // https://codechacha.com/ko/java-run-shell-script/ 참고하자
        try {
            String prepare = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/prepare_helpdogcat_data_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // data prepare 메세지
            System.out.println("prepare = " + prepare);

            String mapreduce = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/build_run_helpdogcat_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // build 메세지
            System.out.println("mapreduce = " + mapreduce);

            String copyToLocal = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/copy_to_local_helpdogcat_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // build 메세지
            System.out.println("copyToLocal = " + copyToLocal);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        //db저장
        saveAnimalDatas.saveData(LocalDate.now().getYear(), "recentdata");


        //데이터 처리
        organizeData.organizeData(curDate.getYear());

    }

    //1년마다 전체 데이터 업데이트 해줄 예정
    @Scheduled(cron = "0 0 2 1 1 *")
    public void getAllAnimalData() throws IOException, ParseException {

        LocalDate curDate = LocalDate.now();

        GetLastYearData glyd = new GetLastYearData();

        glyd.getLastYearData(2017, "alldata", false, serviceKey);

        for (int i = 2018; i <= curDate.getYear() - 2; i++) {
            glyd.getLastYearData(i, "alldata", true, serviceKey);
        }

        //sh코드 실행
        // https://codechacha.com/ko/java-run-shell-script/ 참고하자
        try {
            String prepare = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/prepare_helpdogcat_all_data_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // data prepare 메세지
            System.out.println("prepare = " + prepare);

            String mapreduce = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/build_run_all_data_helpdogcat_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // build 메세지
            System.out.println("mapreduce = " + mapreduce);
//
            String copyToLocal = new ProcessExecutor()
                    .command("sh", "/home/hadoop/a302hadoop/copy_all_data_to_local_helpdogcat_docker.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            // build 메세지
            System.out.println("copyToLocal = " + copyToLocal);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        //db저장
        saveAnimalDatas.saveData(LocalDate.now().getYear() - 2, "alldata");

    }


}
