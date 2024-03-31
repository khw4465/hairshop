package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DesignerService designerService;
    private final CategoryService categoryService;

    /** 어드민 홈 **/
    @GetMapping("/admin")
    public String admin() {
        return "admin/home";
    }

    /** 회원 정보 조회 **/
    @GetMapping("/admin/myPage")
    public String myPage(Model m, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        // 디자이너 정보
        DesignerDto dto = new DesignerDto();
        dto.setName(findDesigner.getName());
        dto.setImg(findDesigner.getImg());
        dto.setContent(findDesigner.getContent());
//        dto.setCareer(findDesigner.getCareer());

        // 스타일 카테고리 정보
        List<StyleSubCategory> subCategoryAll = categoryService.findSubCategoryAll();

        m.addAttribute("designerInfo", dto);
        m.addAttribute("subCategoryAll", subCategoryAll);

        return "admin/designerInfo";
    }
}
