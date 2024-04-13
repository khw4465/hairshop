package com.example.hairshop.api;

import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.StyleCategoryDto;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StyleApiController {

    private final CategoryService categoryService;
    private final StyleService styleService;

    @GetMapping("/api/subCategory")
    public List<StyleCategoryDto> subCategory() {
        List<StyleSubCategory> subCategoryAll = categoryService.findSubCategoryAll();
        List<StyleCategoryDto> subList = subCategoryAll.stream().map(StyleCategoryDto::new).toList();

        return subList;
    }

    @GetMapping("/api/styleBook/category")
    public List<StyleDto> styleBookByCategory(@RequestParam("categoryName") String categoryName,
                                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                                              @RequestParam(value = "limit", defaultValue = "16") int limit) {
        List<StyleDto> styleList = styleService.findPageByCategory(categoryName, offset, limit);
        return styleList;
    }
}
