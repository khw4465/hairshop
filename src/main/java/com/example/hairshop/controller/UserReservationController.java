package com.example.hairshop.controller;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.*;
import com.example.hairshop.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final UserService userService;
    private final MenuService menuService;

    /** 디자이너 선택창 **/
    @GetMapping("/reservation/designer")
    public String selectDesigner(@RequestParam("shopId") String shopId, Model m, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            long id = Long.parseLong(shopId);
            List<DesignerDto> designerDtos = shopService.findDtoById(id).getDesigners();
            m.addAttribute("designers", designerDtos);
            m.addAttribute("shopId", shopId);

            return "/user/reservationDesigner";
        }
        return "redirect:/login/loginForm";
    }

    /** 메뉴 선택창 **/
    @GetMapping("/reservation/menu")
    public String selectMenu(@RequestParam("designerId") String designerId, Model m, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            Designer designer = designerService.findById(designerId);
            DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());
            m.addAttribute("designer", dto);

            ShopDto shopDto = shopService.findDtoById(designer.getShop().getId());
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
        return "redirect:/login/loginForm";
    }

    /** 시간대 선택창 **/
    @GetMapping("/reservation/time")
    public String selectTime(@RequestParam("designerId") String designerId,
                             @RequestParam("menuId") String menuId,
                             HttpSession session,
                             Model m) {
        if (session.getAttribute("userId") != null) {
            Designer designer = designerService.findById(designerId);
            List<ScheduleDto> list = designer.getSchedules().stream().map(ScheduleDto::new).toList();
            m.addAttribute("schedules", list);

            Shop shop = designer.getShop();
            ShopDto dto = new ShopDto(shop.getId(), shop.getOpenTime(), shop.getCloseTime());
            m.addAttribute("shopTime", dto);

            m.addAttribute("designerId", designerId);
            m.addAttribute("menuId", menuId);

            return "/user/reservationTime";
        }
        return "redirect:/login/loginForm";
    }

    /** 예약 화면 **/
    @GetMapping("/reservation")
    public String reservation(@RequestParam("designerId") String designerId,
                              @RequestParam("menuId") String menuId,
                              @RequestParam("date") String selectedDateValue,
                              @RequestParam("time") String selectedTimeValue,
                              HttpSession session,
                              Model m) {
        if (session.getAttribute("userId") != null) {
            //유저 정보
            String userId = session.getAttribute("userId").toString();
            UserDto user = userService.findUserDtoByKakaoId(userId);
            m.addAttribute("user", user);

            //디자이너 정보
            Designer designer = designerService.findById(designerId);
            DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());
            m.addAttribute("designer", dto);

            //샵 이름
            String shopName = designer.getShop().getName();
            m.addAttribute("shop", shopName);

            //메뉴 정보
            MenuDto menu = menuService.findById(menuId);
            m.addAttribute("menu", menu);

            //예약 일시
            m.addAttribute("date", LocalDate.parse(selectedDateValue));
            m.addAttribute("time", LocalTime.parse(selectedTimeValue));

            return "/user/reservation";
        }
        return "redirect:/login/loginForm";
    }

    /** 예약 생성 **/
    @PostMapping("/create/reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationForm form) {
        try {
            ReservationDto reservationDto = reservationService.create(form);
            System.out.println("reservationDto = " + reservationDto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 예약 성공 화면 **/
    @GetMapping("/reservation/success")
    public String success() {
        return "/user/reservationSuccess";
    }

    /** 예약 리스트 **/
    @GetMapping("/reservation/list")
    public String reservationList(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                  @RequestParam(value = "limit", defaultValue = "10") int limit,
                                  HttpSession session, Model m) {
        if (session.getAttribute("userId") != null) {
            String userId = session.getAttribute("userId").toString();
            User user = userService.findUserByKakaoId(userId);

            List<ReservationDto> reservations = reservationService.findByUserId(user.getId(), offset, limit);
            System.out.println("reservations = " + reservations);

            Long count = reservationService.countQueryByUserId(user.getId());
            m.addAttribute("reservationList", reservations);
            m.addAttribute("count", count);
            m.addAttribute("offset", offset);
            m.addAttribute("limit", limit);

            return "/user/reservationList";
        }
        return "redirect:/login/loginForm";
    }

    /** 예약 상세 **/
    @GetMapping("/reservation/detail")
    public String reservationDetail(@RequestParam("reservationId") String reservationId,
                                    HttpSession session,
                                    Model m) {
//        if (session.getAttribute("userId") != null) {
            ReservationDto reservation = reservationService.findById(reservationId);
            System.out.println("reservation = " + reservation);
            m.addAttribute("reservation", reservation);

            return "/user/reservationDetail";
//        }
//        return "redirect:/login/loginForm";
    }
}
