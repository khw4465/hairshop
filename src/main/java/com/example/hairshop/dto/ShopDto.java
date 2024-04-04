package com.example.hairshop.dto;

import com.example.hairshop.domain.ShopImg;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ShopDto {
    private String name;
    private String shopCategory;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String content;
    private List<String> shopImgs;
    private List<MenuDto> menus;
}
