package com.example.hairshop.dto;

import com.example.hairshop.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private int price;
    private String category;

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.category = menu.getCategory().getName();
    }
}
