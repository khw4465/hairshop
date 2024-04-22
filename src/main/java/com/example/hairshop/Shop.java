package com.example.hairshop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class Shop {
    public static void main(String[] args) {

        // 파싱한 데이터를 저장할 변수
        String result = "";

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" +  URLEncoder.encode("524b534168726c61313038654c795571","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_051801","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

            URL url = new URL(urlBuilder.toString());

            BufferedReader bf;

            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            System.out.println("jsonObject = " + jsonObject);
            JSONObject shopInfoResult = (JSONObject)jsonObject.get("LOCALDATA_051801");
            JSONArray shopInfo = (JSONArray)shopInfoResult.get("row");

            for (Object obj : shopInfo) {
                if (obj instanceof JSONObject) {
                    JSONObject json = (JSONObject) obj;
                    Object BPLCNM = json.get("BPLCNM");
                    Object sitewhladdr = json.get("SITEWHLADDR");
                    Object RDNWHLADDR = json.get("RDNWHLADDR");
                    Object SITEPOSTNO = json.get("SITEPOSTNO");
                    Object RDNPOSTNO = json.get("RDNPOSTNO");
                    Object X = json.get("X");
                    Object Y = json.get("Y");
                    Object UPTAENM = json.get("UPTAENM");
                    Object SITETEL = json.get("SITETEL");
                    Object TRDSTATENM = json.get("TRDSTATENM");
                    Object DTLSTATENM = json.get("DTLSTATENM");

//                    System.out.println("사업장명 = " + BPLCNM);
//                    System.out.println("매장주소 = " + sitewhladdr);
//                    System.out.println("도로명주소 = " + RDNWHLADDR);
//                    System.out.println("소재지우편번호 = " + SITEPOSTNO);
//                    System.out.println("도로명우편번호 = " + RDNPOSTNO);
//                    System.out.println("경도 = " + X);
//                    System.out.println("위도 = " + Y);
//                    System.out.println("카테고리 = " + UPTAENM);
//                    System.out.println("전화번호 = " + SITETEL);
//                    System.out.println("영업상태명 = " + TRDSTATENM);
//                    System.out.println("상세영업상태명 = " + DTLSTATENM);
//                    System.out.println();
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
