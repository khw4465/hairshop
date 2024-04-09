package com.example.hairshop.dto;

import com.example.hairshop.domain.StyleSubCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StyleDto {
    private String imgUrl;
    private String category1;
    private String category2;

    public StyleDto(String imgUrl, List<StyleSubCategory> categories) {
        this.imgUrl = imgUrl;
        this.category1 = categories.get(0).getName();
        this.category2 = categories.get(1).getName();
    }
}
