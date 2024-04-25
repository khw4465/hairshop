package com.example.hairshop.controller;

import com.example.hairshop.domain.MyAround;
import com.example.hairshop.dto.AreaDto;
import com.example.hairshop.repository.MyAroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyAroundController {

    @Value("${kakao.javascript_key}")
    private String apiKey;

    private final MyAroundRepository myAroundRepository;
    @GetMapping("/myAround")
    public String myAround(Model m) {
        m.addAttribute("apiKey", apiKey);
        return "myAround";
    }

    @PostMapping("/myAround/newArea")
    public ResponseEntity<?> findByArea(@RequestBody AreaDto areaDto) {
        try {
            double swLat = areaDto.getSwLat();
            double swLng = areaDto.getSwLng();
            double neLat = areaDto.getNeLat();
            double neLng = areaDto.getNeLng();
            List<MyAround> findShop = myAroundRepository.findByArea(swLat, neLat, swLng, neLng);

            return new ResponseEntity<>(findShop, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
