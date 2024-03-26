package com.example.hairshop.controller;

import com.example.hairshop.api.KakaoApi;
import com.example.hairshop.dto.CheckForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @GetMapping("login/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("redirectUri", kakaoRedirectUri);
        model.addAttribute("checkForm", new CheckForm());
        return "login";
    }
}
