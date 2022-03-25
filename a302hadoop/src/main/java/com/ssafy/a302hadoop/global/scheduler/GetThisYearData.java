package com.ssafy.a302hadoop.global.scheduler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class GetThisYearData {


    static int[] day = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public void getThisYearData(LocalDate curDate, String serviceKey) throws IOException, org.json.simple.parser.ParseException {

        //파일 불러옴
//        FileOutputStream fos = new FileOutputStream("~"+File.separator+ "animal"+ File.separator+ "recent.txt", true);
        //recent라는 파일에 이어서 씀
        FileOutputStream fos = new FileOutputStream("C:\\animal\\"+ "recent.txt", true);
        OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter out = new BufferedWriter(writer);

        //현재 년도
        String y = Integer.toString(curDate.getYear());

        //1월부터 현재 달 까지
        for (int i = 1; i <= curDate.getMonthValue(); i++) {
            String curMon = Integer.toString(i);
            if (curMon.length() == 1) curMon = "0" + curMon;

            // 현재가 3/16일이라면 3월달에서는 16일 까지만 구하는거고 나머지 1,2월은 31일 28일 까지 가야하니까
            int month = (i == curDate.getMonthValue() ? curDate.getDayOfMonth() : day[i]);

            for (int j = 1; j <= month; j++) {

                String curDay = Integer.toString(j);
                if (curDay.length() == 1) curDay = "0" + curDay;

                String current = curMon + curDay;

                GetDayCount gdc = new GetDayCount();

                int dayCount = gdc.DayCount(y, curMon, curDay, serviceKey);

                System.out.println(y + "-" + current);

                // 기본이 1000개씩 호출함
                // 예를들어서 3000마리가 있다면 세페이지에 나눠서 호출해줘야함 그 페이지 수를 구하는 거임
                int rp = dayCount / 1000 + (dayCount % 1000 == 0 ? 0 : 1);

                //데이터가 저장될 record
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

                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
                    JSONArray jsonArray = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items")).get("item");

                    for (Object o : jsonArray) {
                        JSONObject obj = (JSONObject) o;
                        record.append((obj.get("kindCd") == null) ? "xxx" : obj.get("kindCd").toString().trim()
                                        .replace(",", "")
                                        .replace("페르시안-페르시안", "페르시안")
                                        .replace("-", " ")
                                ).append(",")

                                .append((obj.get("neuterYn") == null) ? "xxx" : obj.get("neuterYn").toString().trim()
                                        .replace(",", "")
                                        .replace("-", " ")
                                ).append(",")

                                .append((obj.get("processState") == null) ? "xxx" : obj.get("processState").toString().trim().replace(",", "")).append(",")
                                .append((obj.get("age") == null) ? "xxx" : obj.get("age").toString().trim()
                                        .split("\\(")[0]
                                        .replace(",", "")
                                        .replace("~", " ")
                                        .replace("-", " ")
                                        .replace("추정", "")
                                        .replace("월령", "월")
                                        .replace("주령", "주")
                                        .replace("세령", "년")
                                        .replace("년령", "년")
                                        .replace("약", "")
                                        .replace("생후", "")
                                        .replace("Y","년")
                                        .replace("M","개월")
                                        .replace("y","년")
                                        .replace("m","개월")
                                ).append(" ,")
                                .append((obj.get("happenDt") == null) ? "xxx" : obj.get("happenDt").toString().trim().substring(0, 4).replace(",", "")).append(",")
                                .append((obj.get("orgNm") == null) ? "xxx" : obj.get("orgNm").toString().trim().replace(",", ""))
                                .append("\n");
                    }
                }
                out.write(record.toString());
            }
        }
        out.flush();
        out.close();

        System.out.println("done");
    }
}
