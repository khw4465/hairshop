package com.example.hairshop.service;

import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.repository.ShopCategoryRepository;
import com.example.hairshop.repository.StyleMainCategoryRepository;
import com.example.hairshop.repository.StyleSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final StyleMainCategoryRepository styleMainCategoryRepository;
    private final StyleSubCategoryRepository styleSubCategoryRepository;
    private final ShopCategoryRepository shopCategoryRepository;

    /** 메인 카테고리 생성 **/
    @Transactional
    public void saveStyleMainCategory(StyleMainCategory category) {
        styleMainCategoryRepository.save(category);
    }
    /** 서브 카테고리 생성 **/
    @Transactional
    public void saveStyleSubCateogry(StyleSubCategory category) {
        styleSubCategoryRepository.save(category);
    }
    /** 샵 카테고리 생성 **/
    @Transactional
    public void saveShopCategory(ShopCategory category) {
        shopCategoryRepository.save(category);
    }

    // 스타일 카테고리
    /** 메인 카테고리 조회(전체) **/
    public List<StyleMainCategory> findMainCategoryAll() {
        return styleMainCategoryRepository.findAll();
    }

    /** 메인 카테고리 이름으로 조회 **/
    public StyleMainCategory findMainCategoryByName(String name) {
        return styleMainCategoryRepository.findByName(name);
    }
    /** 서브 카테고리 이름으로 조회(단건)**/
    public StyleSubCategory findSubCategory(String name) {
        return styleSubCategoryRepository.findByName(name);
    }

    /** 서브 카테고리 전체 조회 **/
    public List<StyleSubCategory> findSubCategoryAll() {
        return styleSubCategoryRepository.findAll();
    }

    /** 메인 카테고리 이름으로 서브 카테고리 리스트 조회 **/
    public List<StyleSubCategory> findSubCategoryByMainCategoryName(String name) {
        return styleSubCategoryRepository.findByMainCategoryName(name);
    }

    /** 서브 카테고리 이름 조회 **/
    public StyleSubCategory findSubCategoryByName(String name) {
        return styleSubCategoryRepository.findByName(name);
    }

    /** 서브 카테고리의 스타일 조회 **/
    public List<Style> findStyleBySubCategoryName(String name) {
        return styleSubCategoryRepository.findStyle(name);
    }

    // 샵 카테고리
    /** 샵 카테고리 조회(전체) **/
    public List<ShopCategory> findShopCategoryAll() {
        return shopCategoryRepository.findAll();
    }

    /** 샵 카테고리 이름 조회 **/
    public ShopCategory findShopCategoryByName(String name) {
        return shopCategoryRepository.findByName(name);
    }
}
