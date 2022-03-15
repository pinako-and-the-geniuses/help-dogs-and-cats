package com.ssafy.hadoop.global.config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OpenApiDataSave {

    @Value("${openapi.serviceKey}")
    private String serviceKey;

    @Scheduled(cron = "0 0/1 * * * *")
    public void getAnimalData() throws IOException, ParseException {
        // 1. URL을 만들기 위한 StringBuilder.
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");

        String serviceKey = "Hfks4ebltwJpCCvbUxfPihKu8htL%2B8BhXgRwH6IV8lwmJ%2FluwY0%2FF9BZreIvnu%2Fj%2BysmklrcrD3xQoy90uIaqw%3D%3D";

        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("bgnde", "UTF-8") + "=" + URLEncoder.encode("20200301", "UTF-8")); //시작 일자
        urlBuilder.append("&" + URLEncoder.encode("endde", "UTF-8") + "=" + URLEncoder.encode("20200331", "UTF-8")); //종료 일자
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지 번호
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); //줄 수
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML 또는 JSON*/

        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        // 10. 객체 해제.
        rd.close();
        conn.disconnect();

        // 11. 전달받은 데이터 확인.
//        System.out.println(sb.toString());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");

//        while(jj.hasNext()){
//            System.out.println(jj.next().toString());
//        }
        //sexCd 성별
        //kindCd 품종
        //colorCd 색
        //age 나이
        //weight 무게
        //processState 현재 상태
        //neuterYn 중성화 여부 Y-함 N-안함 U-미상
        //specialMark 특징 질병 상태 등등
        //orgNm 지역
        //happenDt 접수일

        //https://www.hadoopinrealworld.com/writing-a-file-to-hdfs-java-program/
//        https://docs.microsoft.com/ko-kr/azure/hdinsight/hadoop/apache-hadoop-develop-deploy-java-mapreduce-linux
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            ans.append(obj.get("kindCd").toString().trim()).append(",").append(obj.get("age").toString().trim()).append("\n");
        }

        FileOutputStream fos = new FileOutputStream("C:\\SSAFY2\\dockertest\\hadooptest\\breed-age.txt", false);
        OutputStreamWriter writer = new OutputStreamWriter(fos,StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(writer);

        out.write(ans.toString());

        out.flush();
        out.close();

    }


}
