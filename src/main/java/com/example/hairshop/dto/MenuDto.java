package com.example.hairshop.dto;

import com.example.hairshop.domain.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private String name;
    private String imgUrl;
    private int price;
    private String category;
}
