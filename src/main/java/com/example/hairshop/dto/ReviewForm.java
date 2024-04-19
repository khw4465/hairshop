package com.example.hairshop.dto;

import lombok.Data;

@Data
public class ReviewForm {
    private double rate;
    private String content;
    private String reservationId;
}
