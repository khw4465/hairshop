package com.example.hairshop.dto;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Reservation;
import com.example.hairshop.domain.Review;
import com.example.hairshop.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private double rate;
    private String content;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.content = review.getContent();
    }
}
