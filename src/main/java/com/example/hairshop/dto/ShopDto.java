package com.example.hairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    private Long id;
    private String name;
    private String shopCategory;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String content;
    private List<String> shopImgs;
    private List<DesignerDto> designers;
    private List<MenuDto> menus;

    public ShopDto(Long id, String name, String shopCategory, String address, List<String> shopImgs) {
        this.id = id;
        this.name = name;
        this.shopCategory = shopCategory;
        this.address = address;
        this.shopImgs = shopImgs;
    }
}
