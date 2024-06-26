package com.example.hairshop.dto;

import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StyleDto {
    private Long id;
    private String imgUrl;
    private String category1;
    private String category2;
    private String designerId;

    public StyleDto(String imgUrl, List<StyleSubCategory> categories) {
        this.imgUrl = imgUrl;
        this.category1 = categories.get(0).getName();
        this.category2 = categories.get(1).getName();
    }

    public StyleDto(Style style) {
        this.id = style.getId();
        this.imgUrl = style.getImgUrl();
        this.category1 = style.getSubCategorys().get(0).getName();
        this.category2 = style.getSubCategorys().get(1).getName();
        this.designerId = style.getDesigner().getId().toString();
    }
}
