package com.example.hairshop.controller;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.*;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ShopService;
import com.example.hairshop.service.StyleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DesignerService designerService;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final StyleService styleService;

    /** 어드민 홈 **/
    @GetMapping("/admin")
    public String admin() {
        return "admin/home";
    }

//--------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------
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

            styleService.save(findDesigner, stylesData);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 스타일 삭제 **/
    @DeleteMapping("/admin/delete/style")
    public ResponseEntity<?> deleteStyle(HttpSession session, @RequestParam("id") Long id) {
        try {
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            styleService.deleteById(findDesigner, id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
//--------------------------------------------------------------------------------------------

    /** 샵 생성 화면 **/
    @GetMapping("/admin/createShop")
    public String shop1(Model m) {
        // 샵 카테고리
        List<ShopCategory> shops = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> shopList = shops.stream()
                .map(c -> new ShopCategoryDto(c.getName())).toList();
        m.addAttribute("shopCategories", shopList);

        // 메뉴 카테고리
        List<MenuCategory> menus = categoryService.findMenuCategoryAll();
        List<MenuCategoryDto> menuList = menus.stream()
                .map(c -> new MenuCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("menuCategories", menuList);

        return "/admin/createShop";
    }

    /** 샵 생성 **/
    @PostMapping("/admin/create/shop")
    public ResponseEntity<?> createShop(HttpSession session, @RequestBody ShopDto shopDto) {
        try{
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            shopService.createShop(findDesigner, shopDto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 샵 수정 화면 **/
    @GetMapping("/admin/modifyShop")
    public String shop2(HttpSession session, Model m) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        //샵 -> DTO로 변경
        Shop findShop = shopService.findByDesigner(findDesigner);
        ShopDto dto = new ShopDto();
        dto.setName(findShop.getName());
        dto.setShopCategory(findShop.getCategory().getName());
        dto.setAddress(findShop.getAddress());
        dto.setOpenTime(findShop.getOpenTime());
        dto.setCloseTime(findShop.getCloseTime());
        dto.setContent(findShop.getContent());
        List<String> imgList = findShop.getShopImgs().stream()
                .map(ShopImg::getImgUrl).toList();
        dto.setShopImgs(imgList);
        List<MenuDto> menuList = findShop.getMenus().stream()
                .map(i -> new MenuDto(i.getName(), i.getImgUrl(), i.getPrice(), i.getCategory().getName())).toList();
        dto.setMenus(menuList);
        m.addAttribute("shop", dto);

        // 샵 카테고리
        List<ShopCategory> shops = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> shopCategoryList = shops.stream()
                .map(c -> new ShopCategoryDto(c.getName())).toList();
        m.addAttribute("shopCategories", shopCategoryList);

        // 메뉴 카테고리
        List<MenuCategory> menus = categoryService.findMenuCategoryAll();
        List<MenuCategoryDto> menuCategoryList = menus.stream()
                .map(c -> new MenuCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("menuCategories", menuCategoryList);

        return "/admin/modifyShop";
    }

    /** 샵 정보 수정 **/
    @PutMapping("/admin/modify/shop")
    public ResponseEntity<?> modifyShop(HttpSession session, @RequestBody ShopDto dto) {
        try{
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            Shop shop = shopService.modifyShop(findDesigner, dto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 샵 삭재 **/
    @DeleteMapping("/admin/remove/shop")
    public ResponseEntity<?> removeShop(HttpSession session) {
        try{
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            shopService.removeShop(findDesigner);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//--------------------------------------------------------------------------------------------
    /** 샵-디자이너 등록 화면 **/
    @GetMapping("admin/registDesigner")
    public String shop_designer(Model m, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        List<Designer> designers = findDesigner.getShop().getDesigners();
        List<DesignerDto> designerDtos = designers.stream()
                .map(d -> new DesignerDto(d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        DesignerDto mainDesigner = designerDtos.get(0); // 메인 디자이너
        m.addAttribute("mainDesigner", mainDesigner);

        if (designers.size() > 1) {
            List<DesignerDto> subDesigner = designerDtos.subList(1, designers.size()); // 서브 디자이너
            m.addAttribute("subDesigner", subDesigner);
        }

        return "/admin/registDesigner";
    }
}
