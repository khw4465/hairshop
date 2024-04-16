package com.example.hairshop.service;

import com.example.hairshop.domain.Menu;
import com.example.hairshop.dto.MenuDto;
import com.example.hairshop.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuDto findById(String menuId) {
        long id = Long.parseLong(menuId);
        Menu menu = menuRepository.findById(id);
        MenuDto dto = new MenuDto(menu);
        return dto;
    }
}
