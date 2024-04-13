package com.example.hairshop.controller;

import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.StyleCategoryDto;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserStyleBookController {

    private final CategoryService categoryService;
    private final StyleService styleService;

    /** 스타일북 **/
    @GetMapping("/styleBook")
    public String styleBook(@RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "16") int limit,
                            Model m) {
        //메인 카테고리
        List<StyleMainCategory> mainCategoryAll = categoryService.findMainCategoryAll();
        List<StyleCategoryDto> mainList = mainCategoryAll.stream().map(StyleCategoryDto::new).toList();
        m.addAttribute("mainCategories", mainList);

        //서브 카테고리
        List<StyleSubCategory> subCategoryAll = categoryService.findSubCategoryAll();
        List<StyleCategoryDto> subList = subCategoryAll.stream().map(StyleCategoryDto::new).toList();
        m.addAttribute("subCategories", subList);

        //스타일
        List<StyleDto> styleList = styleService.findPageAll(offset, limit);
        Long count = styleService.countQueryAll();
        m.addAttribute("styles", styleList);
        m.addAttribute("count", count);
        m.addAttribute("offset", offset);
        m.addAttribute("limit", limit);
        m.addAttribute("searchText", null);
        m.addAttribute("url", "/styleBook");

        return "/user/styleBook";
    }

    /** 카테고리별 스타일북 **/
    @GetMapping("/styleBook/category")
    public String styleBookByCategory(@RequestParam("categoryName") String categoryName,
                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "16") int limit,
                                      Model m) {
        //메인 카테고리
        List<StyleMainCategory> mainCategoryAll = categoryService.findMainCategoryAll();
        List<StyleCategoryDto> mainList = mainCategoryAll.stream().map(StyleCategoryDto::new).toList();
        m.addAttribute("mainCategories", mainList);

        //서브 카테고리
        List<StyleSubCategory> subCategoryAll = categoryService.findSubCategoryAll();
        List<StyleCategoryDto> subList = subCategoryAll.stream().map(StyleCategoryDto::new).toList();
        m.addAttribute("subCategories", subList);

        //스타일
        List<StyleDto> styleList = styleService.findPageByCategory(categoryName, offset, limit);
        Long count = styleService.countQueryByCategory(categoryName);
        m.addAttribute("styles", styleList);
        m.addAttribute("count", count);
        m.addAttribute("offset", offset);
        m.addAttribute("limit", limit);
        m.addAttribute("searchText", categoryName);
        m.addAttribute("url", "/styleBook/category");

        return "/user/styleBook";
    }

    /** 스타일 상세 **/
    @GetMapping("/styleInfo")
    public String styleInfo(Model m) {

        return "/user/styleInfo";
    }
}
