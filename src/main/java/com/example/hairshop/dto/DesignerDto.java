package com.example.hairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignerDto {
    private Long id;
    private String name;
    private String img;
    private String content;
    private String career;
    private List<StyleDto> styles;

    public DesignerDto(String name, String img, String content, String career) {
        this.name = name;
        this.img = img;
        this.content = content;
        this.career = career;
    }

    public DesignerDto(Long id, String name, String img, String content, String career) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.content = content;
        this.career = career;
    }
}
