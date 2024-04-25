package com.example.hairshop.domain;

import com.example.hairshop.repository.StyleMainCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StyleMainCategoryTest{

//    @Autowired
//    StyleMainCategoryRepository styleMainCategoryRepository;
//
//    @Test
//    @Rollback(false)
//    public void 메인카테고리() {
//        StyleMainCategory category1 = new StyleMainCategory("커트");
//        StyleMainCategory category2 = new StyleMainCategory("염색");
//        StyleMainCategory category3 = new StyleMainCategory("펌");
//
//        styleMainCategoryRepository.save(category1);
//        styleMainCategoryRepository.save(category2);
//        styleMainCategoryRepository.save(category3);
//
//        StyleMainCategory one = styleMainCategoryRepository.findOne(category1.getId());
//        StyleMainCategory two = styleMainCategoryRepository.findOne(category2.getId());
//        StyleMainCategory three = styleMainCategoryRepository.findOne(category3.getId());
//
//        assertThat(one.getName()).isEqualTo("커트");
//        assertThat(two.getName()).isEqualTo("염색");
//        assertThat(three.getName()).isEqualTo("펌");
//    }
}