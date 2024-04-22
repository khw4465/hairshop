package com.example.hairshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MyAround {

    @Id @GeneratedValue
    @Column(name = "myAroundShopId")
    private Long id;
    private String shopName;
    private double x;
    private double y;

}
