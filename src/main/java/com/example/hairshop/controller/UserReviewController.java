package com.example.hairshop.controller;

import com.example.hairshop.dto.ReviewForm;
import com.example.hairshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserReviewController {

    private final ReviewService reviewService;

    /** 리뷰 작성 화면 **/
    @GetMapping("/review")
    public String review() {
        return "/user/review";
    }

    /** 리뷰 생성 **/
    @PostMapping("/create/review")
    public ResponseEntity<?> createReview(@RequestBody ReviewForm reviewForm) {
        try {
            reviewService.create(reviewForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
