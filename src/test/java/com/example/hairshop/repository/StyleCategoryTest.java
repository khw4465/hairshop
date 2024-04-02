package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class StyleCategoryTest {

    @Autowired StyleMainCategoryRepository mainCategoryRepository;
    @Autowired StyleSubCategoryRepository subCategoryRepository;
    @Autowired DesignerRepository designerRepository;
    @Autowired StyleRepository styleRepository;
    @BeforeEach
    public void 메인_서브_추가() {
        StyleMainCategory mainCategory1 = new StyleMainCategory("커트");
        StyleMainCategory mainCategory2 = new StyleMainCategory("펌");
        StyleMainCategory mainCategory3 = new StyleMainCategory("염색");
        StyleMainCategory mainCategory4 = new StyleMainCategory("네일");
        StyleMainCategory mainCategory5 = new StyleMainCategory("페디");

        mainCategoryRepository.save(mainCategory1);
        mainCategoryRepository.save(mainCategory2);
        mainCategoryRepository.save(mainCategory3);
        mainCategoryRepository.save(mainCategory4);
        mainCategoryRepository.save(mainCategory5);

        StyleSubCategory subCategory1 = new StyleSubCategory("리프컷");
        StyleSubCategory subCategory2 = new StyleSubCategory("레이어드컷");
        StyleSubCategory subCategory3 = new StyleSubCategory("뱅헤어");
        subCategoryRepository.save(subCategory1);
        subCategoryRepository.save(subCategory2);
        subCategoryRepository.save(subCategory3);

        StyleMainCategory cut = mainCategoryRepository.findByName("커트");
        cut.addSubCategory(subCategory1);
        cut.addSubCategory(subCategory2);
        cut.addSubCategory(subCategory3);

        StyleSubCategory subCategory4 = new StyleSubCategory("히피펌");
        StyleSubCategory subCategory5 = new StyleSubCategory("레이어드펌");
        StyleSubCategory subCategory6 = new StyleSubCategory("S컬펌");
        subCategoryRepository.save(subCategory4);
        subCategoryRepository.save(subCategory5);
        subCategoryRepository.save(subCategory6);

        StyleMainCategory perm = mainCategoryRepository.findByName("펌");
        perm.addSubCategory(subCategory4);
        perm.addSubCategory(subCategory5);
        perm.addSubCategory(subCategory6);

        StyleSubCategory subCategory7 = new StyleSubCategory("브라운");
        StyleSubCategory subCategory8 = new StyleSubCategory("카키");
        StyleSubCategory subCategory9 = new StyleSubCategory("블루블랙");
        subCategoryRepository.save(subCategory7);
        subCategoryRepository.save(subCategory8);
        subCategoryRepository.save(subCategory9);

        StyleMainCategory dye = mainCategoryRepository.findByName("염색");
        dye.addSubCategory(subCategory7);
        dye.addSubCategory(subCategory8);
        dye.addSubCategory(subCategory9);

        StyleSubCategory subCategory10 = new StyleSubCategory("그라데이션");
        StyleSubCategory subCategory11 = new StyleSubCategory("매트");
        StyleSubCategory subCategory12 = new StyleSubCategory("체크");
        subCategoryRepository.save(subCategory10);
        subCategoryRepository.save(subCategory11);
        subCategoryRepository.save(subCategory12);

        StyleMainCategory nail = mainCategoryRepository.findByName("네일");
        nail.addSubCategory(subCategory10);
        nail.addSubCategory(subCategory11);
        nail.addSubCategory(subCategory12);

        StyleSubCategory subCategory13 = new StyleSubCategory("그라데이션");
        StyleSubCategory subCategory14 = new StyleSubCategory("매트");
        StyleSubCategory subCategory15 = new StyleSubCategory("체크");
        subCategoryRepository.save(subCategory13);
        subCategoryRepository.save(subCategory14);
        subCategoryRepository.save(subCategory15);

        StyleMainCategory pedi = mainCategoryRepository.findByName("페디");
        pedi.addSubCategory(subCategory13);
        pedi.addSubCategory(subCategory14);
        pedi.addSubCategory(subCategory15);
    }

    @Test
    @Rollback(false)
    public void 카테고리_스타일추가() {
//        Designer designer = Designer.createDesigner("1234", "디자이너A");
//        designerRepository.save(designer);
//
//        StyleSubCategory cut1 = subCategoryRepository.findByName("리프컷");
//        StyleSubCategory cut2 = subCategoryRepository.findByName("투블럭컷");
//        StyleSubCategory perm1 = subCategoryRepository.findByName("히피펌");
//        StyleSubCategory perm2 = subCategoryRepository.findByName("베이비펌");
//
//
//        Style style1 = Style.addStyle("asdf", designer, cut1, perm1);
//        Style style2 = Style.addStyle("asdf", designer, cut1, perm2);
//        styleRepository.save(style1);
    }
}
