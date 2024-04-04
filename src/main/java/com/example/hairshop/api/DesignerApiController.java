package com.example.hairshop.api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.ShopCategoryDto;
import com.example.hairshop.dto.SubCategoryDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DesignerApiController {

    private final DesignerService designerService;
    private final CategoryService categoryService;

    // 회원 정보 조회
    @GetMapping("/api/myPage")
    public DesignerDto myPage(HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        DesignerDto dto = new DesignerDto();
        dto.setName(findDesigner.getName());
        dto.setImg(findDesigner.getImg());
        dto.setContent(findDesigner.getContent());
        dto.setCareer(findDesigner.getCareer());

        return dto;
    }

    @PostMapping("api/myPage/modify")
    public Designer modifyMyPage(HttpSession session, @RequestBody DesignerDto dto) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        Designer designer = designerService.modifyDesignerInfo(findDesigner, dto.getImg(), dto.getContent(), dto.getCareer());
        return designer;
    }

    @GetMapping("api/admin/style")
    public List<SubCategoryDto> getCategory() {
        List<StyleSubCategory> subCategories = categoryService.findSubCategoryAll();
        List<SubCategoryDto> list = subCategories.stream().map(category -> new SubCategoryDto(category.getId(), category.getName())).toList();

        return list;
    }

    @GetMapping("api/admin/shopCategory")
    public List<ShopCategoryDto> shopCategory() {
        List<ShopCategory> shopCategories = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> list = shopCategories.stream().map(s -> new ShopCategoryDto(s.getName())).toList();
        return list;
    }

}
