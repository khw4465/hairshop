package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
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

    @Transactional
    public Style save(String imgUrl, Designer designer, StyleSubCategory category1, StyleSubCategory category2) {
        Optional<Style> findStyle = styleRepository.findByImg(imgUrl);
        if (findStyle.isEmpty()) {
            Style style = new Style();
            style.setImgUrl(imgUrl);
            style.getSubCategorys().add(category1);
            style.getSubCategorys().add(category2);
            category1.getStyles().add(style);
            category2.getStyles().add(style);

            style.setDesigner(designer);
            designer.getStyles().add(style);

            styleRepository.save(style);
            return style;
        }
        return findStyle.get();
    }

    @Transactional
    public void deleteById(Long id) {
        styleRepository.delete(id);
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
