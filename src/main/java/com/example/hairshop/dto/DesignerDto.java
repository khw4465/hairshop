package com.example.hairshop.dto;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DesignerDto {
    private Long id;
    private String name;
    private String img;
    private String content;
    private String career;
    private String shopName;
    private List<StyleDto> styles;
    private List<ReviewDto> reviews;

    public DesignerDto(Long id, String name, String img, String content, String career) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.content = content;
        this.career = career;
    }

    public DesignerDto(Long id, String name, String img, String content, String career, List<StyleDto> styles) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.content = content;
        this.career = career;
        this.styles = styles;
    }

    public DesignerDto(Designer designer) {
        this.id = designer.getId();
        this.name = designer.getName();
        this.img = designer.getImg();
        this.content = designer.getContent();
        this.career = designer.getCareer();
        this.shopName = designer.getShop().getName();

        List<StyleDto> styles = designer.getStyles().stream().map(StyleDto::new).toList();
        this.styles = styles;

        List<ReviewDto> reviews = designer.getReviews().stream().map(ReviewDto::new).toList();
        this.reviews = reviews;
    }
}
