package com.example.hairshop.dto;

import lombok.Data;

@Data
public class AreaDto {
    private double swLat; // 남서쪽 위도
    private double swLng; // 남서쪽 경도
    private double neLat; // 북동쪽 위도
    private double neLng; // 북동쪽 경도
}
