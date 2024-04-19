package com.example.hairshop.api;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.ShopCategoryDto;
import com.example.hairshop.dto.StyleCategoryDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
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

    @GetMapping("api/admin/style")
    public List<StyleCategoryDto> getCategory() {
        List<StyleSubCategory> subCategories = categoryService.findSubCategoryAll();
        List<StyleCategoryDto> list = subCategories.stream().map(category -> new StyleCategoryDto(category.getId(), category.getName())).toList();

        return list;
    }

    @GetMapping("api/admin/shopCategory")
    public List<ShopCategoryDto> shopCategory() {
        List<ShopCategory> shopCategories = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> list = shopCategories.stream().map(s -> new ShopCategoryDto(s.getName())).toList();
        return list;
    }

    /** 디자이너 전체 조회 **/
    @GetMapping("api/admin/findAll")
    public List<DesignerDto> findAll() {
        List<Designer> all = designerService.findAll();
        List<DesignerDto> list = all.stream()
                .map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        return list;
    }

    /** 디자이너 검색 **/
    @PostMapping("api/admin/search")
    public List<DesignerDto> searchDesigner(@RequestParam("searchInput") String searchInput) {
        List<Designer> searchDesigner = designerService.findByName(searchInput);
        List<DesignerDto> list = searchDesigner.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        return list;
    }

    /** 디자이너 페이징 전체 조회 **/
    @GetMapping("api/pageAll")
    public List<DesignerDto> pageAll(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<DesignerDto> result = designerService.findPageAll(offset, limit);
        return result;
    }
}
