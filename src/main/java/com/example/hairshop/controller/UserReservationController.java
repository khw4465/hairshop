package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Schedule;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.MenuDto;
import com.example.hairshop.dto.ScheduleDto;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ReservationService;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserReservationController {

    private final ReservationService reservationService;
    private final DesignerService designerService;
    private final ShopService shopService;
    private final CategoryService categoryService;

    /** 디자이너 선택창 **/
    @GetMapping("/reservation/designer")
    public String selectDesigner(@RequestParam("shopId") String shopId, Model m) {
        long id = Long.parseLong(shopId);
        List<DesignerDto> designerDtos = shopService.findById(id).getDesigners();
        m.addAttribute("designers", designerDtos);
        m.addAttribute("shopId", shopId);

        return "/user/reservationDesigner";
    }

    /** 메뉴 선택창 **/
    @GetMapping("/reservation/menu")
    public String selectMenu(@RequestParam("designerId") String designerId,
                             Model m) {
        Designer designer = designerService.findById(designerId);
        DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());
        m.addAttribute("designer", dto);

        ShopDto shopDto = shopService.findById(designer.getShop().getId());
        m.addAttribute("shop", shopDto);

        List<MenuDto> menus = shopDto.getMenus();
        m.addAttribute("menus", menus);

        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (MenuDto menu : menus) {
            set.add(menu.getCategory());
        }
        List<String> menuCategoryList = new ArrayList<>(set);
        m.addAttribute("menuCategory", menuCategoryList);

        return "/user/reservationMenu";
    }

    /** 시간대 선택창 **/
    @GetMapping("/reservation/time")
    public String selectTime(@RequestParam("designerId") String designerId,
                             @RequestParam("menuId") String menuId,
                             Model m) {
        Designer designer = designerService.findById(designerId);
        List<ScheduleDto> list = designer.getSchedules().stream().map(ScheduleDto::new).toList();
        m.addAttribute("schedules", list);

        return "/user/reservationTime";
    }
}
