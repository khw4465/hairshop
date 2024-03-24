package com.example.hairshop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.awt.*;

@Entity
@DiscriminatorValue("Nail")
@Getter
public class Nail extends Style {

    @Enumerated(EnumType.STRING)
    private Shape shape;
    private Color color;
}
