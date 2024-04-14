package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.repository.ShopRepository;
import com.example.hairshop.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StyleService {

    private final StyleRepository styleRepository;
    private final CategoryService categoryService;
    private final ShopRepository shopRepository;

    /** 스타일 등록 **/
    @Transactional
    public void save(Designer designer, List<StyleDto> stylesData) {
        //기존에 스타일이 있다면 삭제
        List<Style> originStyle = designer.getStyles();
        if (originStyle.size() > 0) {
            for (Style style : originStyle) {
                List<StyleSubCategory> subCategorys = style.getSubCategorys();
                for (StyleSubCategory subCategory : subCategorys) {
                    subCategory.getStyles().remove(style);
                }
                styleRepository.delete(style);
            }
            originStyle.clear();
        }

        //받아온 값으로 스타일 생성
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

    /** 매장 별 스타일 전체 조회 **/
    public List<StyleDto> findByShop(long id) {
        Shop shop = shopRepository.findOne(id);
        List<Designer> designers = shop.getDesigners();

        List<StyleDto> styleList = new ArrayList<>();
        for (Designer designer : designers) {
            List<Style> styles = designer.getStyles();
            List<StyleDto> styleDtoList = styles.stream().map(StyleDto::new).toList();
            for (StyleDto styleDto : styleDtoList) {
                styleList.add(styleDto);
            }
        }
        return styleList;
    }

    /** 스타일북 페이징 **/
    public List<StyleDto> findPageAll(int offset, int limit) {
        List<Style> pageAll = styleRepository.findPageAll(offset, limit);
        List<StyleDto> styleDtoList = pageAll.stream().map(StyleDto::new).toList();
        return styleDtoList;
    }

    /** 스타일북 카운트 쿼리 **/
    public Long countQueryAll() {
        return styleRepository.countQueryAll();
    }

    /** 스타일북 카테고리별 페이징 **/
    public List<StyleDto> findPageByCategory(String categoryName, int offset, int limit) {
        List<Style> pageByCategory = styleRepository.findPageByCategory(categoryName, offset, limit);
        List<StyleDto> styleDtoList = pageByCategory.stream().map(StyleDto::new).toList();
        return styleDtoList;
    }

    /** 스타일북 카테고리별 카운트 쿼리 **/
    public Long countQueryByCategory(String categoryName) {
        return styleRepository.countQueryByCategory(categoryName);
    }

    public StyleDto findOne(Long id) {
        Style style = styleRepository.findOne(id);
        return new StyleDto(style);
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
