package com.ssafy.hadoop.global.config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GetLastYearData {
    @Value("${openapi.serviceKey}")
    private static String serviceKey;
    static int[] day = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public void getLastYearData(int year, String fileName, boolean append) throws IOException, org.json.simple.parser.ParseException {
        //append가 false면 해당 파일 다시 만듬
        //append가 true면 해당 파일에 이어서 만듬
        FileOutputStream fos = new FileOutputStream("~"+File.separator+ "animal"+ File.separator+ fileName+ ".txt", append);
        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(writer);

        //지난 해들의 데이터를 가져오므로 년도 정보만 있으면 됨
        String y = Integer.toString(year);

        for (int i = 1; i <= 12; i++) {
            String curMon = Integer.toString(i);
            if (curMon.length() == 1) curMon = "0" + curMon;

            for (int j = 1; j <= day[i]; j++) {
                String curDay = Integer.toString(j);
                if (curDay.length() == 1) curDay = "0" + curDay;

                String current = curMon + curDay;

                GetDayCount gdc = new GetDayCount();

                int dayCount = gdc.DayCount(y, curMon, curDay);
                System.out.println(y + "-" + current);

                int rp = dayCount / 1000 + (dayCount % 1000 == 0 ? 0 : 1);

                StringBuilder record = new StringBuilder();
                for (int k = 1; k <= rp; k++) {
                    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");
                    // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
                    urlBuilder.append("?" + URLEncoder.encode("bgnde", "UTF-8") + "=" + URLEncoder.encode(y + current, "UTF-8")); //시작 일자
                    urlBuilder.append("&" + URLEncoder.encode("endde", "UTF-8") + "=" + URLEncoder.encode(y + current, "UTF-8")); //종료 일자
                    urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(k), "UTF-8")); //페이지 번호
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); //줄 수
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
//                    System.out.println("Response code: " + conn.getResponseCode());
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

                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
                    JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");

                    for (Object o : jsonArray) {
                        JSONObject obj = (JSONObject) o;
                        record.append((obj.get("kindCd") == null) ? "xxx" : obj.get("kindCd")).append(",")
                                .append((obj.get("neuterYn") == null) ? "xxx" : obj.get("neuterYn")).append(",")
                                .append((obj.get("processState") == null) ? "xxx" : obj.get("processState")).append(",")
                                .append((obj.get("age") == null) ? "xxx" : obj.get("age")).append(",")
                                .append((obj.get("happenDt") == null) ? "xxx" : obj.get("happenDt")).append(",")
                                .append((obj.get("orgNm") == null) ? "xxx" : obj.get("orgNm"))
                                .append("\n");
                    }

                }
                out.write(record.toString());
            }
        }
        out.flush();
        out.close();
    }
}
