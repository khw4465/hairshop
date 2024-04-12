package com.example.hairshop.dto;

import com.example.hairshop.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private double rate;
    private String content;
    private String user;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.content = review.getContent();
        this.user = review.getUser().getName();
    }
}
