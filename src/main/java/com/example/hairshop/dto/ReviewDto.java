package com.example.hairshop.dto;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Reservation;
import com.example.hairshop.domain.Review;
import com.example.hairshop.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private double rate;
    private String content;
    private LocalDateTime createdDate;
    private String user;
    private String designer;
    private ReservationDto reservation;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.content = review.getContent();
        this.createdDate = review.getCreateDate();
        this.user = review.getUser().getName();
        this.designer = review.getDesigner().getName();
        this.reservation = new ReservationDto(review.getReservation());
    }
}
