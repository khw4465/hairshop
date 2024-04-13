package com.example.hairshop.dto;

import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Data
public class StyleCategoryDto {
    private Long id;
    private String name;
    private String mainName;

    public StyleCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StyleCategoryDto(StyleMainCategory mainCategory) {
        this.id = mainCategory.getId();
        this.name = mainCategory.getName();
    }

    public StyleCategoryDto(StyleSubCategory subCategory) {
        this.id = subCategory.getId();
        this.name = subCategory.getName();
        this.mainName = subCategory.getMainCategory().getName();
    }
}
