package com.example.hairshop.api;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLJsonParser {
    public static JsonObject parseHTMLToJSON(String html) {
        JsonObject jsonData = new JsonObject();

        Document doc = Jsoup.parse(html);

        // 예시: HTML 문서에서 특정 데이터 추출
        String accessToken = doc.select("access_token").text();
        String refreshToken = doc.select("refresh_token").text();

        // JSON 객체 생성
        JsonObject data = new JsonObject();
        data.addProperty("access_token", accessToken);
        data.addProperty("refresh_token", refreshToken);

        jsonData.add("data", data);

        return jsonData;
    }
}
