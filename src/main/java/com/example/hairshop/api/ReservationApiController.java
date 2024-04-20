package com.example.hairshop.api;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.Status;
import com.example.hairshop.dto.ReservationDto;
import com.example.hairshop.dto.ScheduleDto;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ReservationService;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ShopService shopService;
    private final DesignerService designerService;

    @GetMapping("/api/admin/reservation/list")
    public List<ReservationDto> findByShopIdAndStatus(@RequestParam("shopId") String shopId) {
        long id = Long.parseLong(shopId);
        Shop shop = shopService.findById(id);

        List<ReservationDto> dto = reservationService.findByShopId(shop.getId(), Status.예약완료);
        return dto;
    }

    @GetMapping("/api/reservation/time")
    public List<ScheduleDto> findSchedule(@RequestParam("designerId") String designerId) {
        Designer designer = designerService.findById(designerId);
        return designer.getSchedules().stream().map(ScheduleDto::new).toList();
    }
}
