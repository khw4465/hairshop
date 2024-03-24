package com.example.hairshop.dto;

import com.example.hairshop.domain.MenuCategory;
import lombok.Data;

@Data
public class MenuDto {
    private String name;
    private String imgUrl;
    private int price;
    private String content;
    private MenuCategory category;
}
