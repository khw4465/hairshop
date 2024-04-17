package com.example.hairshop.dto;

import lombok.Data;

@Data
public class ReservationForm {
    private String userId;
    private String phoneNum;
    private String designerId;
    private String menuId;
    private String request;
    private String date;
    private String time;
    private int price;
}
