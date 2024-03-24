package com.example.hairshop.dto;

import com.example.hairshop.domain.StyleCategory;
import lombok.Data;

import java.util.List;

@Data
public class StyleDto {
    private String imgUrl;
    private List<StyleCategory> categories;

    public StyleDto(String imgUrl, List<StyleCategory> categories) {
        this.imgUrl = imgUrl;
        this.categories = categories;
    }
}
