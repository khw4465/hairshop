package com.example.hairshop.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.domain.User;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.dto.SubCategoryDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.S3Service;
import com.example.hairshop.service.StyleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DesignerService designerService;
    private final CategoryService categoryService;
    private final StyleService styleService;

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
        dto.setCareer(findDesigner.getCareer());

        m.addAttribute("designerInfo", dto);

        return "admin/designerInfo";
    }

    /** 회원정보 수정 **/
    @PutMapping("/admin/myPage/modify")
    public ResponseEntity<?> modifyMyPage(HttpSession session, @RequestBody DesignerDto dto) {
        try {
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            // 디자이너 정보 수정
            Designer designer = designerService.modifyDesignerInfo(findDesigner, dto.getImg(), dto.getContent(), dto.getCareer());

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 스타일 사진 등록 화면 **/
    @GetMapping("/admin/style")
    public String style(Model m, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        // 회원의 스타일 가져오기
        List<Style> styles = findDesigner.getStyles();
        List<StyleDto> styleDtoList = styles.stream()
                .map(s -> new StyleDto(s.getId(), s.getImgUrl(), s.getSubCategorys())).toList();
        m.addAttribute("styles", styleDtoList);

        // 모든 서브 카테고리 가져오기
        List<StyleSubCategory> subCategories = categoryService.findSubCategoryAll();
        List<SubCategoryDto> categoryDtoList = subCategories.stream().map(c -> new SubCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("subCategories", categoryDtoList);

        return "admin/style";
    }

    /** 스타일 등록 **/
    @PostMapping("/admin/add/style")
    public ResponseEntity<?> addStyle(HttpSession session, @RequestBody List<StyleDto> stylesData) {
        try {
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            for (StyleDto dto : stylesData) {
                StyleSubCategory subCategory1 = categoryService.findSubCategory(dto.getCategory1());
                StyleSubCategory subCategory2 = categoryService.findSubCategory(dto.getCategory2());

                styleService.save(dto.getImgUrl(), findDesigner, subCategory1, subCategory2);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 스타일 삭제 **/
    @DeleteMapping("/admin/delete/style")
    public ResponseEntity<?> deleteStyle(@RequestParam("id") Long id) {
        try {
            styleService.deleteById(id);
            
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
