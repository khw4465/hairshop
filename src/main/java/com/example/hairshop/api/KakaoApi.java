package com.example.hairshop.api;


import com.example.hairshop.dto.KakaoProfile;
import com.example.hairshop.dto.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Log4j2
public class KakaoApi { ;

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;


    /**
     * 3. 로그아웃
     */
    public void kakaoLogout(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while((line = br.readLine()) != null) {
                result+=line;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
