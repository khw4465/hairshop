package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleCategory;
import com.example.hairshop.dto.StyleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DesignerRepositoryTest {

    @Autowired DesignerRepository designerRepository;

    @Test
    @Rollback(false)
    public void 디자이너_생성() {
        List<StyleDto> styles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<StyleCategory> category = new ArrayList<>();
            category.add(new StyleCategory("애쉬펌"));
            category.add(new StyleCategory("핫핑크"));

            StyleDto style = new StyleDto("스타일사진" + i, category);
            styles.add(style);
        }

        Designer designer = Designer.createDesigner("디자이너A", "사진A", "안녕하세요", "~~에서 근무", styles);
        designerRepository.save(designer);
    }
}