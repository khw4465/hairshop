package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Review;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.ReviewDto;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ReviewService;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;
    private final ShopService shopService;

    /** 매장별 리뷰 조회 **/
    @GetMapping("/admin/review/list")
    public String reviewList(@RequestParam("shopId") String shopId,
                             Model m) {
        long id = Long.parseLong(shopId);
        m.addAttribute("shopId", id);

        Shop shop = shopService.findById(id);
        List<Designer> designers = shop.getDesigners();
        List<DesignerDto> dto = designers.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        m.addAttribute("designerList", dto);

        List<ReviewDto> reviewList = reviewService.findByShopId(id);
        m.addAttribute("reviewList", reviewList);
        for (ReviewDto reviewDto : reviewList) {
            System.out.println("reviewDto = " + reviewDto);
        }

        return "/admin/reviewList";
    }

    /**
     * 매장 + 디자이너별 리뷰 조회
     **/
    @GetMapping("/review/select/designer")
    public String reviewByDesigner(@RequestParam("shopId") String shopId,
                                   @RequestParam("designerId") String designerId,
                                   Model m) {
        long id1 = Long.parseLong(shopId);
        m.addAttribute("shopId", id1);

        Shop shop = shopService.findById(id1);
        List<Designer> designers = shop.getDesigners();
        List<DesignerDto> dto = designers.stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        m.addAttribute("designerList", dto);

        if (designerId == "") {
            List<ReviewDto> reviewList = reviewService.findByShopId(id1);
            m.addAttribute("reviewList", reviewList);
        } else {
            long id2 = Long.parseLong(designerId);
            List<ReviewDto> reviews = reviewService.findByShopAndDesigner(id1, id2);
            m.addAttribute("reviewList", reviews);
        }

        return "/admin/reviewList";
    }
}
