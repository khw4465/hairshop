package com.example.hairshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class StyleTip {

    @Id @GeneratedValue
    @Column(name = "styleTipId")
    private Long id;
    private String img;
    private String content;
}
