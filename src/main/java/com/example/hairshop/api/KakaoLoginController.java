package com.example.hairshop.api;

import com.example.hairshop.dto.CheckForm;
import com.example.hairshop.dto.KakaoProfile;
import com.example.hairshop.dto.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Log4j2
public class KakaoLoginController {

    KakaoApi kakaoApi = new KakaoApi();

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @RequestMapping("/login/kakao")
    public String kakaoLogin(@RequestParam("code") String code,
                             @ModelAttribute CheckForm checkForm) {

        log.info("checkForm.designer={}", checkForm.isDesigner());

        //인증코드 요청 전달
        OAuthToken oauthToken = getoAuthToken(code);
        //토큰 전달
        KakaoProfile kakaoProfile = getUserInfo(code, oauthToken);

        System.out.println("카카오 아이디 = " + kakaoProfile.getId());
        System.out.println("카카오 이름 = " + kakaoProfile.getProperties().getNickname());
        System.out.println("카카오 이메일 = " + kakaoProfile.getKakao_account().getEmail());

        return "로그인 성공?";
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("home");
        return mav;
    }


    /**
     * 1. 인증코드 요청 전달
     */
    private OAuthToken getoAuthToken(String code) {
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        //HttpHeader, HttpBody 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http 요청 (POST)
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    /**
     * 2. 인증코드로 토큰 전달
     */
    public KakaoProfile getUserInfo(String code, OAuthToken oauthToken) {
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        //HttpHeader, HttpBody 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        //Http 요청 (POST)
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;
    }
}
