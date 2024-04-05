package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StyleService {

    private final StyleRepository styleRepository;
    private final CategoryService categoryService;

    /** 스타일 등록 **/
    @Transactional
    public void save(Designer designer, List<StyleDto> stylesData) {
        for (StyleDto dto : stylesData) {
            StyleSubCategory subCategory1 = categoryService.findSubCategory(dto.getCategory1());
            StyleSubCategory subCategory2 = categoryService.findSubCategory(dto.getCategory2());

            Optional<Style> findStyle = styleRepository.findByImg(dto.getImgUrl());
            if (findStyle.isEmpty()) {
                Style style = new Style();
                style.setImgUrl(dto.getImgUrl());
                style.getSubCategorys().add(subCategory1);
                style.getSubCategorys().add(subCategory2);
                subCategory1.getStyles().add(style);
                subCategory2.getStyles().add(style);

                style.setDesigner(designer);
                designer.getStyles().add(style);

                styleRepository.save(style);
            }
        }
    }

    /** 스타일 삭제 **/
    @Transactional
    public void deleteById(Designer designer, Long id) {
        Style style = styleRepository.findOne(id);
        if (style != null) {
            List<StyleSubCategory> subCategorys = style.getSubCategorys();
            for (StyleSubCategory subCategory : subCategorys) {
                subCategory.getStyles().remove(style);
            }

            designer.getStyles().remove(style);

            styleRepository.delete(style);
        }
    }

    public Style findOne(Long id) {
        return styleRepository.findOne(id);
    }

    public List<Style> findAll() {
        return styleRepository.findAll();
    }

    public List<Style> findByDesigner(Designer designer) {
        return styleRepository.findStyleByDesigner(designer);
    }

    public List<Style> findByCategory(StyleSubCategory category) {
        return styleRepository.findStyleByCategory(category);
    }
}
