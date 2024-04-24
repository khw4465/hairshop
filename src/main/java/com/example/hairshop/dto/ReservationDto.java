package com.example.hairshop.dto;

import com.example.hairshop.domain.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private String content;
    private String status;
    private LocalDateTime dateTime;
    private int price;
    private UserDto user;
    private DesignerDto designer;
    private ShopDto shop;
    private MenuDto menu;
    private Long reviewId;

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.content = reservation.getContent();
        this.status = reservation.getStatus().name();
        this.dateTime = reservation.getDateTime();
        this.price = reservation.getPrice();
        this.user = new UserDto(reservation.getUser());

        Designer d = reservation.getDesigner();
        this.designer = new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer());

        Shop s = reservation.getShop();
        this.shop = new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream().map(ShopImg::getImgUrl).toList());

        Menu m = reservation.getMenu();
        this.menu = new MenuDto(m);

        if (reservation.getReview() != null) {
            this.reviewId = reservation.getReview().getId();
        }
    }
}
