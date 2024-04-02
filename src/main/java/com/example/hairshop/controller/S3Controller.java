package com.example.hairshop.controller;

import com.example.hairshop.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("img/upload")
    public ResponseEntity<String> imgUpload(@RequestPart("file") MultipartFile file) throws IOException {
        try {
            String imgUrl = s3Service.upload(file);
            return new ResponseEntity<>(imgUrl, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
