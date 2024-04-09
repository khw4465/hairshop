package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.SearchCondition;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.dto.SubCategoryDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DesignerController {

    private final DesignerService designerService;
    private final CategoryService categoryService;


    /** 디자이너 등록 화면 **/
    @GetMapping("/admin/designer")
    public String designer(Model m) {
        List<StyleSubCategory> subCategories = categoryService.findSubCategoryAll();
        List<SubCategoryDto> categoryDtoList = subCategories.stream().map(c -> new SubCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("subCategories", categoryDtoList);

        return "/admin/createDesigner";
    }

    /** 디자이너 등록 **/
    @PostMapping("/admin/create/designer")
    public ResponseEntity<?> createDesigner(@RequestBody DesignerDto dto) {
        try {

            designerService.join(dto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 디자이너 목록 **/
    @GetMapping("/admin/designerList")
    public String designerList(Model m) {

        List<Designer> all = designerService.findAll();
        List<DesignerDto> list = all.stream()
                .map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        m.addAttribute("designerList", list);

        return "/admin/designerList";
    }

    /** 디자이너 검색 **/
    @PostMapping("/admin/search/designer")
    public ResponseEntity<?> searchDesigner(@RequestBody SearchCondition condition) {
        try {
            List<Designer> searchDesigner = designerService.findByName(condition.getName());
            List<DesignerDto> designerList = searchDesigner.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();

            return new ResponseEntity<>(designerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    /** 디자이너 검색 후 추가 **/
    @PostMapping("/admin/search/select")
    public ResponseEntity<?> addDesigner(@RequestBody SearchCondition condition) {
        try {
            Designer designer = designerService.findById(condition.getName());
            DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());

            System.out.println("dto = " + dto);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    /** 디자이너 삭제 **/
    @DeleteMapping("/admin/delete/designer")
    public ResponseEntity<?> deleteDesigner(@RequestParam("id") String id) {
        try {
            designerService.remove(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 디자이너 상세 **/
    @GetMapping("/admin/designerInfo")
    public String designerInfo(@RequestParam("id") String id, Model m) {
        Designer designer = designerService.findById(id);

        List<Style> styles = designer.getStyles();
        List<StyleDto> styleList = styles.stream()
                .map(s -> new StyleDto(s.getImgUrl(), s.getSubCategorys())).toList();

        DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer(), styleList);
        m.addAttribute("designerInfo", dto);

        List<StyleSubCategory> subCategories = categoryService.findSubCategoryAll();
        List<SubCategoryDto> categoryDtoList = subCategories.stream().map(c -> new SubCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("subCategories", categoryDtoList);

        return "/admin/designerInfo";
    }

    /** 디자이너 정보 수정 **/
    @PutMapping("/admin/modify/designer")
    public ResponseEntity<?> modifyDesigner(@RequestBody DesignerDto dto) {
        try {
            designerService.modifyDesignerInfo(dto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
