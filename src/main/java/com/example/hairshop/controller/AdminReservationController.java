package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.Status;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.ReservationDto;
import com.example.hairshop.service.ReservationService;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService reservationService;
    private final ShopService shopService;

    /** 매장별 예약리스트 **/
    @GetMapping("/admin/reservation/list")
    public String reservationList(@RequestParam("shopId") String shopId,
                                  Model m) {
        long id = Long.parseLong(shopId);
        m.addAttribute("shopId", id);

        Shop shop = shopService.findById(id);
        List<Designer> designers = shop.getDesigners();
        List<DesignerDto> dto = designers.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        m.addAttribute("designerList", dto);

        List<ReservationDto> reservations = reservationService.findByShopId(id, Status.예약완료);
        m.addAttribute("reservationList", reservations);

        return "adminReservationList";
    }

    /** 매장, 디자이너별 예약리스트 **/
    @GetMapping("/reservationList/select/designer")
    public String reservationListByDesigner(@RequestParam("shopId") String shopId,
                                            @RequestParam("designerId") String designerId,
                                            Model m) {
        long id1 = Long.parseLong(shopId);
        m.addAttribute("shopId", id1);

        Shop shop = shopService.findById(id1);
        List<Designer> designers = shop.getDesigners();
        List<DesignerDto> dto = designers.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        m.addAttribute("designerList", dto);

        if (designerId == "") {
            List<ReservationDto> reservations = reservationService.findByShopId(id1, Status.예약완료);
            m.addAttribute("reservationList", reservations);
        } else {
            long id2 = Long.parseLong(designerId);
            List<ReservationDto> reservations = reservationService.findByShopAndDesigner(id1, id2, Status.예약완료);
            m.addAttribute("reservationList", reservations);
        }
        return "adminReservationList";
    }

    /** 예약 취소 **/
    @PostMapping("/admin/reservation/cancel")
    public String reservationCancel(@RequestParam("reservationId") String reservationId,
                                    @RequestParam("shopId") String shopId) {
        reservationService.changeCancel(reservationId);

        return "redirect:/admin/reservation/list?shopId="+shopId;
    }
}
