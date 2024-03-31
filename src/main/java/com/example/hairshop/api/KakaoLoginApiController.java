package com.example.hairshop.api;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.User;
import com.example.hairshop.dto.CheckForm;
import com.example.hairshop.dto.KakaoProfile;
import com.example.hairshop.dto.OAuthToken;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class KakaoLoginApiController {

    private final UserService userService;
    private final DesignerService designerService;

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    /** 로그인 요청 **/
    @GetMapping("/login/kakao")
    public ModelAndView kakaoLogin(@RequestParam("code") String code, HttpSession session) {

        //인증코드 요청 전달
        OAuthToken oauthToken = getoAuthToken(code);
        //토큰 전달
        KakaoProfile kakaoProfile = getUserInfo(code, oauthToken);

        // 토큰
        session.setAttribute("accessToken", oauthToken.getAccess_token());
        // 회원아이디
        session.setAttribute("userId", kakaoProfile.getId());
        // 회원 이름
        session.setAttribute("username", kakaoProfile.getProperties().getNickname());
        // 회원 이메일
        session.setAttribute("userEmail", kakaoProfile.getKakao_account().getEmail());

        ModelAndView mav = new ModelAndView();

        Object userId = session.getAttribute("userId");
        mav.addObject("userId", userId);
        mav.setViewName("login");

        return mav;
    }

    /** 타입에 따른 객체 생성후 응답 **/
    @PostMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody CheckForm checkForm, HttpSession session) {
        try {
            String kakaoId = session.getAttribute("userId").toString();
            String username = (String) session.getAttribute("username");
            String userEmail = (String) session.getAttribute("userEmail");

            // 체크박스가 true면 디자이너 생성 false면 일반회원 생성 후 클래스명 반환
            if (checkForm.getIsDesigner() == 1) {
                Designer newDesigner = Designer.createDesigner(kakaoId, username);
                Designer designer = designerService.join(newDesigner);
                return new ResponseEntity<>(designer.getClass().toString(), HttpStatus.OK);
            } else {
                User newUser = User.createUser(kakaoId, username, userEmail);
                User user = userService.join(newUser);
                return new ResponseEntity<>(user.getClass().toString(), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 체크박스 값 응답 메서드 **/
    @PostMapping("/login/check")
    public Map<String, Integer> designerCheck(@RequestBody CheckForm checkForm, HttpSession session) {
        session.setAttribute("isDesigner", checkForm.getIsDesigner());

        Integer isDesigner = (Integer) session.getAttribute("isDesigner");
        System.out.println("isDesigner = " + isDesigner.toString());

        Map<String, Integer> response = new HashMap<>();
        response.put("isDesigner", isDesigner);
        return response;
    }

    /** 로그아웃 **/
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {

        kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");

        return new RedirectView("/");
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
