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
