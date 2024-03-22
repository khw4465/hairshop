package com.example.hairshop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.awt.*;

@Entity
@DiscriminatorValue("Nail")
@Getter
public class Nail extends Style {

    private Shape shape;
    private Color color;
}
